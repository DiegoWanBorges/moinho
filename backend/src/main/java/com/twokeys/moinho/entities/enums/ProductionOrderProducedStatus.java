package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionOrderProducedStatus {
	LIBERADO(0),
	SOB_ANALISE(1),
	REJEITADO(2);
	
	
	private int productionOrderProducedStatus;
	
	private ProductionOrderProducedStatus(int productionOrderProducedStatus) {
		this.productionOrderProducedStatus=productionOrderProducedStatus;
	}
	
	public int getProductionOrderProducedStatus() {
		return productionOrderProducedStatus;
	}
	
	public static ProductionOrderProducedStatus valueOf(int status) {
		for (ProductionOrderProducedStatus value : ProductionOrderProducedStatus.values()) {
			if (value.getProductionOrderProducedStatus()==status) {
				return value;
			}
		}
		throw new ValidationException("Status not found!");
	}
}	
