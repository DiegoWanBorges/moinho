package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;

public class ProductionOrderItemsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Double quantity;
	private Double cost;
	private Integer cancel;
	private Instant dateCancel;
	private ProductionOrderItemsType type;
	private Long productionOrederId;
	private ProductDTO product;
	public ProductionOrderItemsDTO() {
	}

	public ProductionOrderItemsDTO(Long productionOrederId, ProductDTO product, Double quantity, Double cost,
								   Integer cancel, Instant dateCancel, ProductionOrderItemsType type) {
		this.productionOrederId = productionOrederId;
		this.product = product;
		this.quantity = quantity;
		this.cost = cost;
		this.cancel = cancel;
		this.dateCancel = dateCancel;
		this.type = type;
	}
	public ProductionOrderItemsDTO(ProductionOrderItems entity) {
		this.productionOrederId = entity.getProductionOrder().getId();
		this.product = new ProductDTO(entity.getProduct());
		this.quantity = entity.getQuantity();
		this.cost = entity.getCost();
		this.cancel = entity.getCancel();
		this.dateCancel = entity.getDateCancel();
		this.type = entity.getType();
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

	public Long getProductionOrederId() {
		return productionOrederId;
	}

	public void setProductionOrederId(Long productionOrederId) {
		this.productionOrederId = productionOrederId;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
}
