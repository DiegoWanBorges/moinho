package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum FormulationType {
	INTERMEDIARIO(0),
	ACABADO(1);
	
	private int formulationType;
	

	private FormulationType(int formulationType) {
		this.formulationType=formulationType;
	}
	
	public int getFormulationType() {
		return formulationType;
	}
	public static FormulationType valueOf(int type) {
		for (FormulationType value : FormulationType.values()) {
			if (value.getFormulationType()==type) {
				return value;
			}
		}
		throw new ValidationException();
	}
}
