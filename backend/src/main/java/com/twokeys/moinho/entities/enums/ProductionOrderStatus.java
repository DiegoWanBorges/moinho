package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionOrderStatus {
	ABERTO(0),
	ENCERRADO(1),
	CANCELADO(2);
	
	
	private int productionOrderStatus;
	
	private ProductionOrderStatus(int productionOrderStatus) {
		this.productionOrderStatus=productionOrderStatus;
	}
	
	public int getProductionOrderStatus() {
		return productionOrderStatus;
	}
	
	public static ProductionOrderStatus valueOf(int status) {
		for (ProductionOrderStatus value : ProductionOrderStatus.values()) {
			if (value.getProductionOrderStatus()==status) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
