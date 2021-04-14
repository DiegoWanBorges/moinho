package com.twokeys.moinho.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.twokeys.moinho.entities.pk.ProductionOrderProductProducedPK;
@Entity
@Table(name="production_order_produced")
public class ProductionOrderProduced {
	
	@EmbeddedId
	private ProductionOrderProductProducedPK id = new ProductionOrderProductProducedPK();
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant manufacturingDate;
	private String lote;
	private Double quantity;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant creatAt;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant cancelDate;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public ProductionOrderProduced(){	
	}

	public ProductionOrderProduced(ProductionOrder productionOrder,Integer pallet, Instant manufacturingDate, String lote,
			Double quantity, Instant creatAt, Instant cancelDate, Product product) {
		this.id.setProductionOrder(productionOrder);
		this.id.setPallet(pallet);
		this.manufacturingDate = manufacturingDate;
		this.lote = lote;
		this.quantity = quantity;
		this.creatAt = creatAt;
		this.cancelDate = cancelDate;
		this.product = product;
	}

	public ProductionOrder getProductionOrder() {
		return id.getProductionOrder();
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.id.setProductionOrder(productionOrder);
	}
	
	public Integer getPallet() {
		return id.getPallet();
	}

	public void setPallet(Integer pallet) {
		this.id.setPallet(pallet);
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

	public Instant getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(Instant creatAt) {
		this.creatAt = creatAt;
	}

	public Instant getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Instant cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
	
}
