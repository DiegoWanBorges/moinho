package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum CostCalculationStatus {
	ANDAMENTO(0),
	ENCERRADO(1),
	CANCELADO(2);
	
	private int costCalculationStatus;
	
	private CostCalculationStatus(int costCalculationStatus) {
		this.costCalculationStatus=costCalculationStatus;
	}
	
	public int getCostCalculationStatus() {
		return costCalculationStatus;
	}
	
	public static CostCalculationStatus valueOf(int status) {
		for (CostCalculationStatus value : CostCalculationStatus.values()) {
			if (value.getCostCalculationStatus()==status) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
