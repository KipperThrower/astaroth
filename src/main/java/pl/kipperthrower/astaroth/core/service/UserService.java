package pl.kipperthrower.astaroth.core.service;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.Group;
import pl.kipperthrower.astaroth.core.domain.User;

@SuppressWarnings("deprecation")
@Service("userService")
public class UserService {

	private PasswordEncoder encoder = new ShaPasswordEncoder();

	@Autowired
	private DaoFactory daoFactory;
	@Autowired
    private SessionRegistry sessionRegistry;

	public User findByUsername(String username) {
		User user = daoFactory.getDao(User.class).findOneByCriteria(
				Restrictions.eq(User.USERNAME, username));
		return user;
	}

	@Transactional
	public void installNewUser(User user) {
		user.setSalt(String.valueOf(System.currentTimeMillis()));
		user.setPassword(encoder.encodePassword(user.getPassword(),
				user.getSalt()));
		user.setDateCreated(new Date());
		daoFactory.getDao(User.class).save(user);
	}

	@Transactional
	public void installNewGroup(Group group) {
		daoFactory.getDao(Group.class).save(group);
	}
	
	@Transactional
	public User getLoggedUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return findByUsername(username);
	}
	
	@Transactional
	public void lockUser(String name) {
		User user = findByUsername(name);
		if (user != null) {
			user.setAccountNonLocked(false);
		}
		daoFactory.getDao(User.class).save(user);
	}
	
	@Transactional
	public List<User> getAllLoggedUsers() {
		return Lists.transform(sessionRegistry.getAllPrincipals(), new Function<Object, User>() {

			@Override
			public User apply(Object input) {
				return (User) input;
			}
		});
	}

}
