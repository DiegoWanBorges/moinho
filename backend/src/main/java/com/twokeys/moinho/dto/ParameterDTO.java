package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Parameter;
import com.twokeys.moinho.entities.enums.CostType;

public class ParameterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String companyName;
	private boolean	productionOrderWithoutStock;	
	private CostType typeCostUsed;
	
	public ParameterDTO() {
	}

	public ParameterDTO(Long id, String companyName, boolean productionOrderWithoutStock, CostType typeCostUsed) {
		this.id = id;
		this.companyName = companyName;
		this.productionOrderWithoutStock = productionOrderWithoutStock;
		this.typeCostUsed = typeCostUsed;
	}
	public ParameterDTO(Parameter entity) {
		this.id = entity.getId();
		this.companyName = entity.getCompanyName();
		this.productionOrderWithoutStock = entity.isProductionOrderWithoutStock();
		this.typeCostUsed = entity.getTypeCostUsed();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public boolean isProductionOrderWithoutStock() {
		return productionOrderWithoutStock;
	}

	public void setProductionOrderWithoutStock(boolean productionOrderWithoutStock) {
		this.productionOrderWithoutStock = productionOrderWithoutStock;
	}

	public CostType getTypeCostUsed() {
		return typeCostUsed;
	}

	public void setTypeCostUsed(CostType typeCostUsed) {
		this.typeCostUsed = typeCostUsed;
	}
	

}
