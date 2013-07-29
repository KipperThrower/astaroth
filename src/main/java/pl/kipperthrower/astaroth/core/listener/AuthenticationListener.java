package pl.kipperthrower.astaroth.core.listener;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.AuthenticationEvent;
import pl.kipperthrower.astaroth.core.domain.User;
import pl.kipperthrower.astaroth.core.service.UserService;

@Component
public class AuthenticationListener implements
		ApplicationListener<AbstractAuthenticationEvent> {

	private static final Logger LOGGER = Logger
			.getLogger(AuthenticationListener.class);

	@Autowired
	private DaoFactory daoFactory;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		if (event instanceof InteractiveAuthenticationSuccessEvent) {
			return;
		}
		
		AuthenticationEvent authEvent = prepareAuthEvent(event);
		daoFactory.getDao(AuthenticationEvent.class).save(authEvent);

		String auditMessage = "Login attempt with username: "
				+ authEvent.getUsername() + " Success: "
				+ authEvent.isAuthenticated() + " IP: "
				+ authEvent.getIpAddress();
		LOGGER.info(auditMessage);
	}

	private AuthenticationEvent prepareAuthEvent(AbstractAuthenticationEvent authEvent) {
		Authentication authentication = authEvent.getAuthentication();
		AuthenticationEvent event = new AuthenticationEvent();
		event.setAuthenticated(authentication.isAuthenticated());
		event.setUsername(authentication.getName());
		event.setDate(new DateTime(authEvent.getTimestamp() * 1000).toDate());
		User user = userService.findByUsername(event.getUsername());
		event.setUser(user);
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails details = (WebAuthenticationDetails) authentication
					.getDetails();
			event.setIpAddress(details.getRemoteAddress());
			event.setSessionId(details.getSessionId());
		}
		return event;
	}

}
