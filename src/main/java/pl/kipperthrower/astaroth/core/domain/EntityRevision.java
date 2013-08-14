package pl.kipperthrower.astaroth.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import pl.kipperthrower.astaroth.core.listener.EntityRevisionListener;

@Entity
@Table(name = "entity_revision")
@RevisionEntity(EntityRevisionListener.class)
public class EntityRevision implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@RevisionNumber
	private Long id;
	@RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	/**
	 * Who changed the entity
	 */
	@Column
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
