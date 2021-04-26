package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.twokeys.moinho.entities.pk.ProductionOrderCostLaborPK;	

@Entity
@Table(name="tb_production_order_cost_labor")
public class ProductionOrderCostLabor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	ProductionOrderCostLaborPK id = new ProductionOrderCostLaborPK(); 
	
	private Double value;
	
	public ProductionOrderCostLabor() {
	}

	public ProductionOrderCostLabor(ProductionOrder productionOrder,Sector sector, Double value) {
		this.id.setProductionOrder(productionOrder);
		this.id.setSector(sector);
		this.value = value;
	}

	public ProductionOrder getProductionOrder() {
		return id.getProductionOrder();
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.id.setProductionOrder(productionOrder);
	}
	
	public Sector getSector() {
		return id.getSector();
	}

	public void setSector(Sector sector) {
		this.id.setSector(sector);
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
		ProductionOrderCostLabor other = (ProductionOrderCostLabor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductionOrderCostLabor [id=" + id + ", value=" + value + "]";
	}
	
}
