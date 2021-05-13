package com.twokeys.moinho.entities.pk;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.entities.ProductionOrder;

@Embeddable
public class ProductionOrderOperationalCostPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="production_order_id")
	private ProductionOrder productionOrder;
	
	@ManyToOne
	@JoinColumn(name="apportionmentType_id")
	private OperationalCostType apportionmentType;
	
	public ProductionOrderOperationalCostPK() {
	}

	public ProductionOrderOperationalCostPK(ProductionOrder productionOrder, OperationalCostType apportionmentType) {
		this.productionOrder = productionOrder;
		this.apportionmentType = apportionmentType;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public OperationalCostType getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(OperationalCostType apportionmentType) {
		this.apportionmentType = apportionmentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apportionmentType == null) ? 0 : apportionmentType.hashCode());
		result = prime * result + ((productionOrder == null) ? 0 : productionOrder.hashCode());
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
		ProductionOrderOperationalCostPK other = (ProductionOrderOperationalCostPK) obj;
		if (apportionmentType == null) {
			if (other.apportionmentType != null)
				return false;
		} else if (!apportionmentType.equals(other.apportionmentType))
			return false;
		if (productionOrder == null) {
			if (other.productionOrder != null)
				return false;
		} else if (!productionOrder.equals(other.productionOrder))
			return false;
		return true;
	}
	
	
	
	
}
