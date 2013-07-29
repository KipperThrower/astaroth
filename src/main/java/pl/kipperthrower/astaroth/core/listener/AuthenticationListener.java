package pl.kipperthrower.astaroth.core.listener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.AuthenticationEvent;
import pl.kipperthrower.astaroth.core.domain.User;
import pl.kipperthrower.astaroth.core.service.UserService;

@Component
public class AuthenticationListener implements
		ApplicationListener<AbstractAuthenticationEvent> {

	public static final int ALLOWED_LOGIN_ATTEMPTS = 5;
	public static final int MINUTES_BEFORE_BLOCKED = 5;

	private static final Logger LOGGER = Logger
			.getLogger(AuthenticationListener.class);

	@Autowired
	private DaoFactory daoFactory;
	@Autowired
	private UserService userService;
	private LoadingCache<String, Integer> loginAttemptsCache;

	@PostConstruct
	public void init() {
		loginAttemptsCache = CacheBuilder.newBuilder()
				.expireAfterWrite(MINUTES_BEFORE_BLOCKED, TimeUnit.MINUTES)
				.build(new CacheLoader<String, Integer>() {

					@Override
					public Integer load(String key) throws Exception {
						return 0;
					}
					
				});
	}

	@Override
	@Transactional
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		logAuthenticationEvent(event);
		lockAccountIfToManyFails(event);
	}
	
	/**
	 * Brute force protection
	 * @param event
	 */
	private void lockAccountIfToManyFails(AbstractAuthenticationEvent event) {
		String username = event.getAuthentication().getName();
		if (event instanceof AbstractAuthenticationFailureEvent) {
			try {
				Integer attempts = loginAttemptsCache.get(username);
				attempts++;
				loginAttemptsCache.put(username, attempts);
				if (attempts > ALLOWED_LOGIN_ATTEMPTS) {
					userService.lockUser(username);
				}
			} catch (ExecutionException e) {
				LOGGER.error(e, e);
			}
		}
		if (event instanceof AuthenticationSuccessEvent) {
			loginAttemptsCache.invalidate(username);
		}
	}

	/**
	 * Log access history
	 * @param event
	 */
	private void logAuthenticationEvent(AbstractAuthenticationEvent event) {
		if (event instanceof InteractiveAuthenticationSuccessEvent) {
			return;
		}

		AuthenticationEvent authEvent = mapToAuthEvent(event);
		daoFactory.getDao(AuthenticationEvent.class).save(authEvent);

		String auditMessage = "Login attempt with username: "
				+ authEvent.getUsername() + " Success: "
				+ authEvent.isAuthenticated() + " IP: "
				+ authEvent.getIpAddress();
		LOGGER.info(auditMessage);
	}

	private AuthenticationEvent mapToAuthEvent(
			AbstractAuthenticationEvent authEvent) {

		Authentication authentication = authEvent.getAuthentication();
		AuthenticationEvent event = new AuthenticationEvent();
		event.setAuthenticated(authentication.isAuthenticated());
		event.setUsername(authentication.getName());
		event.setDate(new DateTime(authEvent.getTimestamp()).toDate());
		event.setEventType(authEvent.getClass().getSimpleName());
		User user = userService.findByUsername(event.getUsername());
		event.setUser(user);
		if (authEvent instanceof AuthenticationSwitchUserEvent) {
			AuthenticationSwitchUserEvent asue = (AuthenticationSwitchUserEvent) authEvent;
			event.setSwitch(true);
			User targetUser = userService.findByUsername(asue.getTargetUser()
					.getUsername());
			event.setTargetUser(targetUser);
		}
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication
					.getDetails();
			event.setIpAddress(details.getRemoteAddress());
			event.setSessionId(details.getSessionId());
		}
		return event;
	}

}
