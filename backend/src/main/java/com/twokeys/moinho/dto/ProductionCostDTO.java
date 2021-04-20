package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionCost;

public class ProductionCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant date;
	private ApportionmentTypeDTO productionApportionment;
	private Double paymentAmount;
	
	public ProductionCostDTO() {
	}

	public ProductionCostDTO(Long id, Instant date, ApportionmentTypeDTO productionApportionment,
							 Double paymentAmount) {
		this.id = id;
		this.date = date;
		this.productionApportionment = productionApportionment;
		this.paymentAmount = paymentAmount;
	}
	public ProductionCostDTO(ProductionCost entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.productionApportionment = new ApportionmentTypeDTO(entity.getProductionApportionment());
		this.paymentAmount = entity.getPaymentAmount();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public ApportionmentTypeDTO getProductionApportionment() {
		return productionApportionment;
	}

	public void setProductionApportionment(ApportionmentTypeDTO productionApportionment) {
		this.productionApportionment = productionApportionment;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	


}
