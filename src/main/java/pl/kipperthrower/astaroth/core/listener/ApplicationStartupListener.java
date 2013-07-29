package pl.kipperthrower.astaroth.core.listener;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pl.kipperthrower.astaroth.core.domain.Group;
import pl.kipperthrower.astaroth.core.domain.User;
import pl.kipperthrower.astaroth.core.service.UserService;

import freemarker.log.Logger;

@Component
public class ApplicationStartupListener implements
		ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = Logger.getLogger(this.getClass().getName());

	@Value("#{wiringProperties['default.admin.username']}")
	private String defaultAdminUsername;
	@Value("#{wiringProperties['default.admin.password']}")
	private String defaultAdminPassword;
	@Value("#{wiringProperties['default.admin.installOnStartup']}")
	private boolean installDefaultAdminUser;

	@Autowired
	private UserService userService;

	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (installDefaultAdminUser && defaultAdminUsername != null
				&& defaultAdminPassword != null) {

			User admin = userService.findByUsername(defaultAdminUsername);

			if (admin == null) {

				admin = new User();
				admin.setUsername(defaultAdminUsername);
				admin.setPassword(defaultAdminPassword);
				admin.setEmail(defaultAdminUsername + "@astaroth.com");

				Group userGroup = new Group();
				userGroup.setName(Group.ROLE_USER);
				userService.installNewGroup(userGroup);

				Group adminGroup = new Group();
				adminGroup.setName(Group.ROLE_ADMIN);
				userService.installNewGroup(adminGroup);
				
				Group godGroup = new Group();
				godGroup.setName(Group.ROLE_GOD);
				userService.installNewGroup(godGroup);

				admin.setGroups(new ArrayList<Group>(3));
				admin.getGroups().add(userGroup);
				admin.getGroups().add(adminGroup);
				admin.getGroups().add(godGroup);

				userService.installNewUser(admin);
				log.info("Installed default admin account with username="
						+ defaultAdminUsername);
			}
		}
	}

}
