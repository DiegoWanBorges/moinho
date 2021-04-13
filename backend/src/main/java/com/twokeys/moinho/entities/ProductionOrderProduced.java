package com.twokeys.moinho.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.twokeys.moinho.entities.pk.ProductionOrderProductProducedPK;
@Entity
@Table(name="production_order_produced")
public class ProductionOrderProduced {
	
	@EmbeddedId
	private ProductionOrderProductProducedPK id;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant manufacturingDate;
	private String lote;
	private Double quantity;
	
	public ProductionOrderProduced(){	
	}

	public ProductionOrderProduced(ProductionOrder productionOrder, Product product,Instant manufacturingDate, 
										  String lote, Double quantity) {
		this.id.setProductionOrder(productionOrder);
		this.id.setProduct(product);
		this.manufacturingDate = manufacturingDate;
		this.lote = lote;
		this.quantity = quantity;
	}

	public ProductionOrder getProductionOrder() {
		return id.getProductionOrder();
	}

	public void setId(ProductionOrder productionOrder) {
		this.id.setProductionOrder(productionOrder);
	}
	
	public Product getProduct() {
		return id.getProduct();
	}

	public void setId(Product product) {
		this.id.setProduct(product);
	}

	public Instant getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Instant manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
}
