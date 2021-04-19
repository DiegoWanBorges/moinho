package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionCost;

public class ProductionCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant payDay;
	private ProductionApportionmentDTO productionApportionment;
	private Double paymentAmount;
	
	public ProductionCostDTO() {
	}

	public ProductionCostDTO(Long id, Instant payDay, ProductionApportionmentDTO productionApportionment,
							 Double paymentAmount) {
		this.id = id;
		this.payDay = payDay;
		this.productionApportionment = productionApportionment;
		this.paymentAmount = paymentAmount;
	}
	public ProductionCostDTO(ProductionCost entity) {
		this.id = entity.getId();
		this.payDay = entity.getPayDay();
		this.productionApportionment = new ProductionApportionmentDTO(entity.getProductionApportionment());
		this.paymentAmount = entity.getPaymentAmount();
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

	public ProductionApportionmentDTO getProductionApportionment() {
		return productionApportionment;
	}

	public void setProductionApportionment(ProductionApportionmentDTO productionApportionment) {
		this.productionApportionment = productionApportionment;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	


}
