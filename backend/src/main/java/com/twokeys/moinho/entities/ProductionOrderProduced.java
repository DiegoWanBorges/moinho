package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.twokeys.moinho.entities.pk.ProductionOrderProducedPK;
@Entity
@Table(name="tb_production_order_produced")
public class ProductionOrderProduced implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ProductionOrderProducedPK id = new ProductionOrderProducedPK();
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant manufacturingDate;
	private String lote;
	private Double quantity;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name="produced_product_status_id")
	private ProducedProductStatus producedProductStatus;
	private Long stockId;
	public ProductionOrderProduced(){	
	}

	public ProductionOrderProduced(ProductionOrder productionOrder,Integer pallet, Instant manufacturingDate, String lote,
								   Double quantity,   Product product,ProducedProductStatus producedProductStatus,
								   Long stockId) {
		this.id.setProductionOrder(productionOrder);
		this.id.setPallet(pallet);
		this.manufacturingDate = manufacturingDate;
		this.lote = lote;
		this.quantity = quantity;
		this.product = product;
		this.producedProductStatus=producedProductStatus;
		this.stockId=stockId;
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
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProducedProductStatus getProducedProductStatus() {
		return producedProductStatus;
	}

	public void setProducedProductStatus(ProducedProductStatus producedProductStatus) {
		this.producedProductStatus = producedProductStatus;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
	
	
	
}
