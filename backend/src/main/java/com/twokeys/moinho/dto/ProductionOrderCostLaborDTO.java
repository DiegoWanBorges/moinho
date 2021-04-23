package com.twokeys.moinho.dto;

import com.twokeys.moinho.entities.ProductionOrderCostLabor;

public class ProductionOrderCostLaborDTO {
	private Long productionOrderId;
	private SectorDTO sector;
	private Double value;
	
	public ProductionOrderCostLaborDTO() {
	}

	public ProductionOrderCostLaborDTO(Long productionOrderId, SectorDTO sector, Double value) {
		this.productionOrderId = productionOrderId;
		this.sector = sector;
		this.value = value;
	}
	public ProductionOrderCostLaborDTO(ProductionOrderCostLabor entity) {
		this.productionOrderId = entity.getProductionOrder().getId();
		this.sector = new SectorDTO( entity.getSector());
		this.value = entity.getValue();
	}

	public Long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(Long productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public SectorDTO getSector() {
		return sector;
	}

	public void setSector(SectorDTO sector) {
		this.sector = sector;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	
}
