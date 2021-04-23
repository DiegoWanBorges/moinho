package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.ProductionOrderOperationalCost;

public class ProductionOrderOperationalCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long productionOrderId;
	private ApportionmentTypeDTO apportionmentType;
	private Double value;
	
	public ProductionOrderOperationalCostDTO() {
	}

	public ProductionOrderOperationalCostDTO(Long productionOrderId, ApportionmentTypeDTO apportionmentType,
											 Double value) {
		this.productionOrderId = productionOrderId;
		this.apportionmentType = apportionmentType;
		this.value = value;
	}
	public ProductionOrderOperationalCostDTO(ProductionOrderOperationalCost entity) {
		this.productionOrderId =entity.getProductionOrder().getId();
		this.apportionmentType = new ApportionmentTypeDTO(entity.getApportionmentType());
		this.value = entity.getValue();
	}

	public Long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(Long productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public ApportionmentTypeDTO getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(ApportionmentTypeDTO apportionmentType) {
		this.apportionmentType = apportionmentType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
