package pl.kipperthrower.astaroth.core.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.google.common.base.Splitter;

@Entity
@Table(name = "jobs")
@org.hibernate.annotations.Table(appliesTo = "jobs", indexes = {
		@Index(name = "jobs_user_id_fk", columnNames = { "user_id" }),
		@Index(name = "jobs_error", columnNames = { "error_occured" }),
		@Index(name = "jobs_exec_date_started", columnNames = { "executor",
				"date_started" }) })
public class Job extends AbstractEntity {

	public static final String D_USER = "user";
	public static final String D_PARAMETERS = "parameters";
	public static final String D_EXECUTOR = "executor";
	public static final String D_DATE_CREATED = "dateCreated";
	public static final String D_DATE_STARTED = "dateStarted";
	public static final String D_DATE_FINISHED = "dateFinished";
	public static final String D_ERROR_MESSAGE = "errorMessage";
	public static final String D_RESULT = "result";
	public static final String D_STATUS = "status";

	private static final long serialVersionUID = 1L;

	/**
	 * Who created the job
	 */
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class, optional = false)
	@JoinColumn(name = "user_id")
	private User user;
	/**
	 * Parameters in URL query string style e.g. param1=value1&param2=value2
	 */
	@Column(nullable = false)
	private String parameters;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private JobExecutor executor;
	@Column(name = "date_created", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column(name = "date_started")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStarted;
	@Column(name = "date_finished")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateFinished;
	@Column(name = "error_message")
	private String errorMessage;
	@Column
	private String result;
	@Column
	@Enumerated(EnumType.STRING)
	private JobStatus status;

	/**
	 * Parsed parameters
	 */
	@Transient
	private Map<String, String> parametersMap;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		parametersMap = null;
		this.parameters = parameters;
	}

	public JobExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(JobExecutor executor) {
		this.executor = executor;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Date getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(Date dateFinished) {
		this.dateFinished = dateFinished;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public JobStatus getStatus() {
		return status;
	}

	public void setStatus(JobStatus status) {
		this.status = status;
	}
	
	public String getParameter(String key) {
		if (parametersMap == null) {
			parametersMap = Splitter.on("&").withKeyValueSeparator("=")
					.split(parameters);
		}
		return parametersMap.get(key);
	}

}
