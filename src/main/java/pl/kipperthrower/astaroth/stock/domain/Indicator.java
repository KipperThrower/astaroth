package pl.kipperthrower.astaroth.stock.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import pl.kipperthrower.astaroth.core.domain.AbstractEntity;

@Entity
@Table(name = "indicators")
public class Indicator extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String code;
	@Column(nullable = false)
	private String name;
	@Column
	private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
