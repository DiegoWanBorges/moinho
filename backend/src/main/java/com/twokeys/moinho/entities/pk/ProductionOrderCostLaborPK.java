package com.twokeys.moinho.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.Sector;

@Embeddable
public class ProductionOrderCostLaborPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="production_order_id")
	private ProductionOrder productionOrder;
	@ManyToOne
	@JoinColumn(name="sector_id")
	private Sector sector;
	
	public ProductionOrderCostLaborPK() {
	}

	public ProductionOrderCostLaborPK(ProductionOrder productionOrder, Sector sector) {
		this.productionOrder = productionOrder;
		this.sector = sector;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productionOrder == null) ? 0 : productionOrder.hashCode());
		result = prime * result + ((sector == null) ? 0 : sector.hashCode());
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
		ProductionOrderCostLaborPK other = (ProductionOrderCostLaborPK) obj;
		if (productionOrder == null) {
			if (other.productionOrder != null)
				return false;
		} else if (!productionOrder.equals(other.productionOrder))
			return false;
		if (sector == null) {
			if (other.sector != null)
				return false;
		} else if (!sector.equals(other.sector))
			return false;
		return true;
	}
	
	
	
}
