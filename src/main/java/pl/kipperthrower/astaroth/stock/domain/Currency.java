package pl.kipperthrower.astaroth.stock.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import pl.kipperthrower.astaroth.core.domain.AbstractEntity;

@Entity
@Table(name = "currencies")
@org.hibernate.annotations.Table(appliesTo = "currencies", indexes = { @Index(name = "currencies_code", columnNames = { "code" }) })
public class Currency extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String code;
	@Column(nullable = false)
	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
