package pl.kipperthrower.astaroth.core.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.kipperthrower.astaroth.core.service.AuthenticationEventService;

@Component
public class AuthenticationListener implements
		ApplicationListener<AbstractAuthenticationEvent> {

	@Autowired
	private AuthenticationEventService authenticationEventService;

	@Override
	@Transactional
	public void onApplicationEvent(AbstractAuthenticationEvent event) {
		authenticationEventService.logAuthenticationEvent(event);
		authenticationEventService.lockAccountIfToManyFails(event);
	}

}
