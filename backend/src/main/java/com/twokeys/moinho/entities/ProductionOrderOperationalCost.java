package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.twokeys.moinho.entities.pk.ProductionOrderOperationalCostPK;

@Entity
@Table(name="tb_production_order_operational_cost")
public class ProductionOrderOperationalCost implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	ProductionOrderOperationalCostPK id = new ProductionOrderOperationalCostPK();
	private Double value;
	
	public ProductionOrderOperationalCost() {
	}

	public ProductionOrderOperationalCost(ProductionOrder productionOrder,OperationalCostType operationalCostType, Double value) {
		this.id.setProductionOrder(productionOrder);
		this.id.setOperationalCostType(operationalCostType);
		this.value = value;
	}

	public ProductionOrder getProductionOrder() {
		return id.getProductionOrder();
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.id.setProductionOrder(productionOrder);
	}
	
	public OperationalCostType getOperationalCostType() {
		return id.getOperationalCostType();
	}

	public void setOperationalCostType(OperationalCostType operationalCostType) {
		this.id.setOperationalCostType(operationalCostType);
	}

	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
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
		ProductionOrderOperationalCost other = (ProductionOrderOperationalCost) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
