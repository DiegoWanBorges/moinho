package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.ProductionOrderOperationalCost;

public class ProductionOrderOperationalCostDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long productionOrderId;
	private OperationalCostTypeDTO operationalCostType;
	private Double value;
	
	public ProductionOrderOperationalCostDTO() {
	}

	public ProductionOrderOperationalCostDTO(Long productionOrderId, OperationalCostTypeDTO operationalCostType,
											 Double value) {
		this.productionOrderId = productionOrderId;
		this.operationalCostType = operationalCostType;
		this.value = value;
	}
	public ProductionOrderOperationalCostDTO(ProductionOrderOperationalCost entity) {
		this.productionOrderId =entity.getProductionOrder().getId();
		this.operationalCostType = new OperationalCostTypeDTO(entity.getOperationalCostType());
		this.value = entity.getValue();
	}

	public Long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(Long productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public OperationalCostTypeDTO getOperationalCostType() {
		return operationalCostType;
	}

	public void setOperationalCostType(OperationalCostTypeDTO operationalCostType) {
		this.operationalCostType = operationalCostType;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}
