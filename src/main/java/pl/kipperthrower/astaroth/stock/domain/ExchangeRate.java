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
@Table(name = "exchange_rates", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"currency_id", "period", "date" }) })
@org.hibernate.annotations.Table(appliesTo = "exchange_rates", indexes = {
		@Index(name = "exchange_rates_currency_id_fk", columnNames = { "currency_id" }),
		@Index(name = "exchange_rates_curr_per_dat", columnNames = {
				"currency_id", "period", "date" }) })
public class ExchangeRate extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = Currency.class, optional = false)
	@JoinColumn(name = "currency_id", nullable = false)
	private Currency currency;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal open;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal high;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal low;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal close;
	@Column(nullable = false, precision = 10, scale = 5)
	private BigDecimal volume;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PeriodOHLC period;

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PeriodOHLC getPeriod() {
		return period;
	}

	public void setPeriod(PeriodOHLC period) {
		this.period = period;
	}
}
