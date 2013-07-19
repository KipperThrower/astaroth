package pl.kipperthrower.astaroth.core.services;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.kipperthrower.astaroth.core.dao.DaoFactory;
import pl.kipperthrower.astaroth.core.domain.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private DaoFactory daoFactory;

	@Transactional
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = daoFactory.getDao(User.class).findOneByCriteria(
				Restrictions.eq(User.USERNAME, username));
		if (user == null) {
			throw new UsernameNotFoundException(username + " not found");
		}
		return user;
	}
}
