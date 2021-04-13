package com.twokeys.moinho.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;

@Embeddable
public class ProductionOrderProductProducedPK implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="production_order_id")
	private ProductionOrder productionOrder;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public ProductionOrderProductProducedPK() {
	}

	public ProductionOrderProductProducedPK(ProductionOrder productionOrder, Product product) {
		this.productionOrder = productionOrder;
		this.product = product;
	}

	public ProductionOrder getProductionOrder() {
		return productionOrder;
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.productionOrder = productionOrder;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (productionOrder == null) {
			if (other.productionOrder != null)
				return false;
		} else if (!productionOrder.equals(other.productionOrder))
			return false;
		return true;
	}

	
	
	
	
}
