package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionOrderItemType {
	NORMAL(0),
	COMPLEMENTAR(1),
	RETORNO(2),
	EXTRA(3),;
	
	
	private int productionOrderItemsType;
	
	private ProductionOrderItemType(int productionOrderItemsType) {
		this.productionOrderItemsType=productionOrderItemsType;
	}
	
	public int getProductionOrderItemsType() {
		return productionOrderItemsType;
	}
	
	public static ProductionOrderItemType valueOf(int type) {
		for (ProductionOrderItemType value : ProductionOrderItemType.values()) {
			if (value.getProductionOrderItemsType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
