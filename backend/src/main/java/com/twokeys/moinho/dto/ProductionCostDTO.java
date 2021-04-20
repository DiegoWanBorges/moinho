package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionCost;

public class ProductionCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant date;
	private ApportionmentTypeDTO apportionmentType;
	private Double paymentAmount;
	private String description;
	
	public ProductionCostDTO() {
	}

	public ProductionCostDTO(Long id, Instant date, ApportionmentTypeDTO apportionmentType,
							 Double paymentAmount,String description) {
		this.id = id;
		this.date = date;
		this.apportionmentType = apportionmentType;
		this.paymentAmount = paymentAmount;
		this.description=description;
	}
	public ProductionCostDTO(ProductionCost entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.apportionmentType = new ApportionmentTypeDTO(entity.getApportionmentType());
		this.paymentAmount = entity.getPaymentAmount();
		this.description=entity.getDescription();
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

	public ApportionmentTypeDTO getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(ApportionmentTypeDTO apportionmentType) {
		this.apportionmentType = apportionmentType;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	


}
