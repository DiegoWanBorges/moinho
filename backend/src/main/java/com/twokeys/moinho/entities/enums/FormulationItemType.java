package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum FormulationItemType {
	NORMAL(0),
	EXTRA(1);
	
	
	private int formulationItemsType;
	
	private FormulationItemType(int formulationItemsType) {
		this.formulationItemsType=formulationItemsType;
	}
	
	public int getformulationItemsType() {
		return formulationItemsType;
	}
	
	public static FormulationItemType valueOf(int type) {
		for (FormulationItemType value : FormulationItemType.values()) {
			if (value.getformulationItemsType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}	
