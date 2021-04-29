package com.twokeys.moinho.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.ProductionOrderItemType;
import com.twokeys.moinho.entities.pk.ProductionOrderItemPK;

@Entity
@Table(name="tb_production_order_item")
public class ProductionOrderItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ProductionOrderItemPK id = new ProductionOrderItemPK();
	
	private Long stockId;
	private Double quantity;
	private Double cost; 
	private ProductionOrderItemType type;
	private boolean rawMaterial;
	@ManyToOne
	@JoinColumn(name="occurrence_id",nullable = true)
	private Occurrence occurrence;
		
	public ProductionOrderItem() {
	}

	public ProductionOrderItem(ProductionOrder productionOrder, Product product, Integer serie, Long stockId,
							   Double quantity, Double cost,ProductionOrderItemType type,
							   Occurrence occurrence,boolean rawMaterial) {
		this.id.setProductionOrder(productionOrder);
		this.id.setProduct(product);
		this.id.setSerie(serie);
		this.stockId=stockId;
		this.quantity = quantity;
		this.cost = cost;
		this.type = type;
		this.occurrence=occurrence;
		this.rawMaterial=rawMaterial;
	}

	public ProductionOrder getProductionOrder() {
		return id.getProductionOrder();
	}

	public void setProductionOrder(ProductionOrder productionOrder) {
		this.id.setProductionOrder(productionOrder);
	}
	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		this.id.setProduct(product);
	}
	public Integer getSerie() {
		return id.getSerie();
	}

	public void setSerie(Integer serie) {
		this.id.setSerie(serie);
	}
	
	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public ProductionOrderItemType getType() {
		return type;
	}

	public void setType(ProductionOrderItemType type) {
		this.type = type;
	}
	
	public Occurrence getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
	}
	
	public boolean getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(boolean rawMaterial) {
		this.rawMaterial = rawMaterial;
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
		ProductionOrderItem other = (ProductionOrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
