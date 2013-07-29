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
	@Column
	private String ipAddress;
	@Column
	private String sessionId;
	@Column(nullable = false)
	private boolean authenticated;
	@Column
	private String eventType;
	@Column
	private boolean isSwitch;
	@Column
	private User targetUser;

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

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public boolean isSwitch() {
		return isSwitch;
	}

	public void setSwitch(boolean isSwitch) {
		this.isSwitch = isSwitch;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

}
