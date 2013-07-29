package pl.kipperthrower.astaroth.core.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.springframework.security.core.GrantedAuthority;

import pl.kipperthrower.astaroth.core.domain.User;

@Entity
@Table(name = "groups")
@org.hibernate.annotations.Table(appliesTo = "groups", indexes = { @Index(name = "groups_name", columnNames = { "name" }) })
public class Group extends AbstractEntity implements GrantedAuthority {

	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_GOD = "ROLE_GOD";

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String name;
	@ManyToMany
	@JoinTable(name = "users_groups", 
		joinColumns = @JoinColumn(name = "group_id"), 
		inverseJoinColumns = @JoinColumn(name = "user_id"))
	@OrderBy("id")
	private List<User> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthority() {
		return name;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

}
