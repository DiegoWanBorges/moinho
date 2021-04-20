package com.twokeys.moinho.entities.enums;

import javax.validation.ValidationException;

public enum ProductionApportionmentType {
	VOLUME_MATERIA_PRIMA(0),
	TEMPO_PRODUCAO(1);
	private int productionApportionmentType;
	
	private ProductionApportionmentType(int productionApportionmentType) {
		this.productionApportionmentType=productionApportionmentType;
	}
	
	public int getProductionApportionmentType() {
		return productionApportionmentType;
	}
	
	public static ProductionApportionmentType valueOf(int type) {
		for (ProductionApportionmentType value : ProductionApportionmentType.values()) {
			if (value.getProductionApportionmentType()==type) {
				return value;
			}
		}
		throw new ValidationException("Tipo de rateio não encontrado!");
	}
}	
