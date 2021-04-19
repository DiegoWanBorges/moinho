package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionCost;

public class ProductionCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Instant date;
	private FormulationApportionmentTypeDTO productionApportionment;
	private Double paymentAmount;
	
	public ProductionCostDTO() {
	}

	public ProductionCostDTO(Long id, Instant date, FormulationApportionmentTypeDTO productionApportionment,
							 Double paymentAmount) {
		this.id = id;
		this.date = date;
		this.productionApportionment = productionApportionment;
		this.paymentAmount = paymentAmount;
	}
	public ProductionCostDTO(ProductionCost entity) {
		this.id = entity.getId();
		this.date = entity.getDate();
		this.productionApportionment = new FormulationApportionmentTypeDTO(entity.getProductionApportionment());
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

	public FormulationApportionmentTypeDTO getProductionApportionment() {
		return productionApportionment;
	}

	public void setProductionApportionment(FormulationApportionmentTypeDTO productionApportionment) {
		this.productionApportionment = productionApportionment;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	


}
