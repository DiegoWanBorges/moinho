package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;
import com.twokeys.moinho.entities.pk.ProductionOrderItemsPK;

@Entity
@Table(name="production_order_items")
public class ProductionOrderItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ProductionOrderItemsPK id = new ProductionOrderItemsPK();
	
	private Long consumptionNumber;
	private Double quantity;
	private Double cost; 
	private Instant dateCancel;
	private ProductionOrderItemsType type;
	
	@ManyToOne
	@JoinColumn(name="occurrence_id",nullable = true)
	private Occurrence occurrence;
	
	public ProductionOrderItems() {
	}

	public ProductionOrderItems(ProductionOrder productionOrder, Product product, Integer serie, Long consumptionNumber,
								Double quantity, Double cost, Instant dateCancel,ProductionOrderItemsType type,
								Occurrence occurrence) {
		this.id.setProductionOrder(productionOrder);
		this.id.setProduct(product);
		this.id.setSerie(serie);
		this.consumptionNumber=consumptionNumber;
		this.quantity = quantity;
		this.cost = cost;
		this.dateCancel = dateCancel;
		this.type = type;
		this.occurrence=occurrence;
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
	
	public Long getConsumptionNumber() {
		return consumptionNumber;
	}

	public void setConsumptionNumber(Long consumptionNumber) {
		this.consumptionNumber = consumptionNumber;
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

	public Instant getDateCancel() {
		return dateCancel;
	}

	public void setDateCancel(Instant dateCancel) {
		this.dateCancel = dateCancel;
	}

	public ProductionOrderItemsType getType() {
		return type;
	}

	public void setType(ProductionOrderItemsType type) {
		this.type = type;
	}

	
	
	public Occurrence getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(Occurrence occurrence) {
		this.occurrence = occurrence;
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
		ProductionOrderItems other = (ProductionOrderItems) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
}
