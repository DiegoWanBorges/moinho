package com.twokeys.moinho.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.ProductionOrder;

@Embeddable
public class ProductionOrderItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="production_order_id")
	private ProductionOrder productionOrder;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	private Integer serie;
	
	public ProductionOrderItemPK() {
	}

	public ProductionOrderItemPK(ProductionOrder productionOrder, Product product,Integer serie) {
		this.productionOrder = productionOrder;
		this.product = product;
		this.serie=serie;
	
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

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	
}
