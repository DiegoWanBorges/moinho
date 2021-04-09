package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionOrderItemsType {
	ABERTO(0),
	ENCERRADO(1),
	CANCELADO(2);
	
	
	private int productionOrderItemsType;
	
	private ProductionOrderItemsType(int productionOrderItemsType) {
		this.productionOrderItemsType=productionOrderItemsType;
	}
	
	public int getProductionOrderItemsType() {
		return productionOrderItemsType;
	}
	
	public static ProductionOrderItemsType valueOf(int type) {
		for (ProductionOrderItemsType value : ProductionOrderItemsType.values()) {
			if (value.getProductionOrderItemsType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
