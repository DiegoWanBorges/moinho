package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.entities.enums.ProductionOrderItemsType;

public class ProductionOrderItemsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long consumptionNumber;
	private Integer serie;
	private Double quantity;
	private Double cost;
	private Integer cancel;
	private Instant dateCancel;
	private ProductionOrderItemsType type;
	private Long productionOrderId;
	private ProductDTO product;
	
	public ProductionOrderItemsDTO() {
	}

	public ProductionOrderItemsDTO(Long productionOrderId, ProductDTO product, Integer serie,Long consumptionNumber,
								   Double quantity, Double cost,
								   Integer cancel, Instant dateCancel, ProductionOrderItemsType type) {
		this.productionOrderId = productionOrderId;
		this.product = product;
		this.serie=serie;
		this.consumptionNumber=consumptionNumber;
		this.quantity = quantity;
		this.cost = cost;
		this.cancel = cancel;
		this.dateCancel = dateCancel;
		this.type = type;
	}
	
	public ProductionOrderItemsDTO(ProductionOrderItems entity) {
		this.productionOrderId = entity.getProductionOrder().getId();
		this.product = new ProductDTO(entity.getProduct());
		this.serie=entity.getSerie();
		this.consumptionNumber=entity.getConsumptionNumber();
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

	public Long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(Long productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public Integer getSerie() {
		return serie;
	}

	public void setSerie(Integer serie) {
		this.serie = serie;
	}

	public Long getConsumptionNumber() {
		return consumptionNumber;
	}

	public void setConsumptionNumber(Long consumptionNumber) {
		this.consumptionNumber = consumptionNumber;
	}
	
}
