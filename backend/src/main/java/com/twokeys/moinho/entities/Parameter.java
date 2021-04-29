package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.CostType;

@Entity
@Table(name="tb_parameter")
public class Parameter   implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private Long id;
	private String companyName;
	private boolean	productionOrderWithoutStock;	
	private CostType typeCostUsed;
	
	public Parameter() {
	}

	public Parameter(Long id, String companyName, boolean productionOrderWithoutStock, CostType typeCostUsed) {
		this.id = id;
		this.companyName = companyName;
		this.productionOrderWithoutStock = productionOrderWithoutStock;
		this.typeCostUsed = typeCostUsed;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parameter other = (Parameter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
