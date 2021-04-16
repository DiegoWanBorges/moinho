package com.twokeys.moinho.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.ProductionOrder;

@Embeddable
public class ProductionOrderProductProducedPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="production_order_id")
	private ProductionOrder productionOrder;
	private Integer pallet;
	
	public ProductionOrderProductProducedPK() {
	}

	public ProductionOrderProductProducedPK(ProductionOrder productionOrder, Integer pallet) {
		this.productionOrder = productionOrder;
		this.pallet = pallet;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Integer getPallet() {
		return pallet;
	}

	public void setPallet(Integer pallet) {
		this.pallet = pallet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pallet == null) ? 0 : pallet.hashCode());
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
		ProductionOrderProductProducedPK other = (ProductionOrderProductProducedPK) obj;
		if (pallet == null) {
			if (other.pallet != null)
				return false;
		} else if (!pallet.equals(other.pallet))
			return false;
		if (productionOrder == null) {
			if (other.productionOrder != null)
				return false;
		} else if (!productionOrder.equals(other.productionOrder))
			return false;
		return true;
	}
}
