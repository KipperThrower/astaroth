package pl.kipperthrower.astaroth.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import pl.kipperthrower.astaroth.domain.User;

@Entity
@Table(name="Groups")
public class Group extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;
    @ManyToMany(mappedBy="groups")
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
