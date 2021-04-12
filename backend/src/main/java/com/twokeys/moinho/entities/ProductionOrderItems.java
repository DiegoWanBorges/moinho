package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;
import com.twokeys.moinho.entities.pk.ProductionOrderItemsPK;

@Entity
@Table(name="production_order_items")
public class ProductionOrderItems implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private ProductionOrderItemsPK id;
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long consumptioNumber;
	private Double quantity;
	private Double cost;
	private Integer cancel;
	private Instant dateCancel;
	private ProductionOrderItemsType type;
	
	public ProductionOrderItems() {
	}

	public ProductionOrderItems(ProductionOrder productionOrder, Product product,Integer serie , Long consumptioNumber, Double quantity, Double cost,
						        Integer cancel, Instant dateCancel, ProductionOrderItemsType type) {
		super();
		this.id.setProductionOrder(productionOrder);
		this.id.setProduct(product);
		this.id.setSerie(serie);
		this.consumptioNumber = consumptioNumber;
		this.quantity = quantity;
		this.cost = cost;
		this.cancel = cancel;
		this.dateCancel = dateCancel;
		this.type = type;
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

	public Long getConsumptioNumber() {
		return consumptioNumber;
	}

	public void setConsumptioNumber(Long consumptioNumber) {
		this.consumptioNumber = consumptioNumber;
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

	public Integer getCancel() {
		return cancel;
	}

	public void setCancel(Integer cancel) {
		this.cancel = cancel;
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
