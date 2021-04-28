package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionOrderItemsType {
	NORMAL(0),
	COMPLEMENTAR(1),
	RETORNO(2),
	EXTRA(3),;
	
	
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
