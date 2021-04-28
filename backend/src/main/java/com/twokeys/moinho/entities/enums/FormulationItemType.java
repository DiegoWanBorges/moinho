package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum FormulationItemsType {
	NORMAL(0),
	EXTRA(1);
	
	
	private int formulationItemsType;
	
	private FormulationItemsType(int formulationItemsType) {
		this.formulationItemsType=formulationItemsType;
	}
	
	public int getformulationItemsType() {
		return formulationItemsType;
	}
	
	public static FormulationItemsType valueOf(int type) {
		for (FormulationItemsType value : FormulationItemsType.values()) {
			if (value.getformulationItemsType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
