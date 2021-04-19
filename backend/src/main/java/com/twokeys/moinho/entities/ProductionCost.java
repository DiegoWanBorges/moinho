package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="productionCost")
public class ProductionCost   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant payDay;
	
	@ManyToOne
	@JoinColumn(name="productionApportionment_id")
	private ProductionApportionment productionApportionment;
	
	private Double paymentAmount;
	
	public ProductionCost() {
	}

	public ProductionCost(Long id, Instant payDay, ProductionApportionment productionApportionment,
			Double paymentAmount) {
		super();
		this.id = id;
		this.payDay = payDay;
		this.productionApportionment = productionApportionment;
		this.paymentAmount = paymentAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getPayDay() {
		return payDay;
	}

	public void setPayDay(Instant payDay) {
		this.payDay = payDay;
	}

	public ProductionApportionment getProductionApportionment() {
		return productionApportionment;
	}

	public void setProductionApportionment(ProductionApportionment productionApportionment) {
		this.productionApportionment = productionApportionment;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionCost other = (ProductionCost) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
