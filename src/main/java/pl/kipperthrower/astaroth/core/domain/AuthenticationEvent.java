package pl.kipperthrower.astaroth.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class AuthenticationEvent extends AbstractEntity {

	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = User.class)
	private User user;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private Date date;
	@Column(nullable = false)
	private String ipAddress;
	@Column(nullable = false)
	private boolean authenticated;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

}
