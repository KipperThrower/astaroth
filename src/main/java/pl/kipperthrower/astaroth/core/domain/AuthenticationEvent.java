package pl.kipperthrower.astaroth.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "authentication_events")
@org.hibernate.annotations.Table(appliesTo = "authentication_events", indexes = {
		@Index(name = "authentication_events_user_id_fk", columnNames = { "user_id" }),
		@Index(name = "authentication_events_target_user_id_fk", columnNames = { "target_user_id" }) })
public class AuthenticationEvent extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "session_id")
	private String sessionId;
	@Column(nullable = false)
	private boolean authenticated;
	@Column(name = "event_type")
	private String eventType;
	@Column(name = "switch_user", nullable = false)
	private boolean switchUser;
	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = User.class)
	@JoinColumn(name = "target_user_id")
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

	public boolean isSwitchUser() {
		return switchUser;
	}

	public void setSwitchUser(boolean switchUser) {
		this.switchUser = switchUser;
	}

	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}

}
