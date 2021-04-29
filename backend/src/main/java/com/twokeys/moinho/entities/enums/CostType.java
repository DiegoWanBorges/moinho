package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum CostType {
	CUSTO_ULTIMA_ENTRADA(0),
	CUSTO_MEDIO(1);
	
	private int costType;
	

	private CostType(int costType) {
		this.costType=costType;
	}
	
	public int getCostType() {
		return costType;
	}
	public static CostType valueOf(int type) {
		for (CostType value : CostType.values()) {
			if (value.getCostType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}
