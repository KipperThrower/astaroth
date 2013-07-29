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
@Table(name = "runtime_errors")
@org.hibernate.annotations.Table(appliesTo = "runtime_errors", 
	indexes = { @Index(name = "runtime_errors_user_id_fk", columnNames = { "user_id" }) })
public class RuntimeError extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "exception_class")
	private String exceptionClass;
	@Column
	private String stack;
	@Column
	private String url;
	@Column
	private String ip;
	@Column(name = "http_method")
	private String httpMethod;
	@Column(name = "query_string")
	private String queryString;
	@Column(name = "request_body")
	private String requestBody;
	@Column
	private String charset;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getExceptionClass() {
		return exceptionClass;
	}

	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}
