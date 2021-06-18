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
	@JoinColumn(name="operationalCostType_id")
	private OperationalCostType operationalCostType;
	
	public ProductionOrderOperationalCostPK() {
	}

	public ProductionOrderOperationalCostPK(ProductionOrder productionOrder, OperationalCostType operationalCostType) {
		this.productionOrder = productionOrder;
		this.operationalCostType = operationalCostType;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public OperationalCostType getOperationalCostType() {
		return operationalCostType;
	}

	public void setOperationalCostType(OperationalCostType operationalCostType) {
		this.operationalCostType = operationalCostType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operationalCostType == null) ? 0 : operationalCostType.hashCode());
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
		if (operationalCostType == null) {
			if (other.operationalCostType != null)
				return false;
		} else if (!operationalCostType.equals(other.operationalCostType))
			return false;
		if (productionOrder == null) {
			if (other.productionOrder != null)
				return false;
		} else if (!productionOrder.equals(other.productionOrder))
			return false;
		return true;
	}

		
}
