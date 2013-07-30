package pl.kipperthrower.astaroth.stock.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

import pl.kipperthrower.astaroth.core.domain.AbstractEntity;

@Entity
@Table(name = "indicator_values", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"indicator_id", "currency_id", "period", "date" }) })
@org.hibernate.annotations.Table(appliesTo = "indicator_values", indexes = {
		@Index(name = "indicator_values_indicator_fk", columnNames = { "indicator_id" }),
		@Index(name = "indicator_values_currency_fk", columnNames = { "currency_id" }),
		@Index(name = "indicator_values_ind_per_dat", columnNames = {
				"indicator_id", "period", "date" }) })
public class IndicatorValue extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = Indicator.class, optional = false)
	@JoinColumn(name = "indicator_id", nullable = false)
	private Indicator indicator;
	@ManyToOne(targetEntity = Currency.class, optional = false)
	@JoinColumn(name = "currency_id", nullable = false)
	private Currency currency;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal value;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodOHLC period;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Indicator getIndicator() {
		return indicator;
	}

	public void setIndicator(Indicator indicator) {
		this.indicator = indicator;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public PeriodOHLC getPeriod() {
		return period;
	}

	public void setPeriod(PeriodOHLC period) {
		this.period = period;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
