package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.ProductionOrderItem;
import com.twokeys.moinho.entities.enums.ProductionOrderItemType;

public class ProductionOrderItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long stockId;
	private Integer serie;
	private Double quantity;
	private Double cost;
	private ProductionOrderItemType type;
	private boolean rawMaterial;
	private Long productionOrderId;
	private ProductDTO product;
	private OccurrenceDTO occurrence;
	
	public ProductionOrderItemDTO() {
	}

	public ProductionOrderItemDTO(Long productionOrderId, ProductDTO product, Integer serie,Long stockId,
								   Double quantity, Double cost, ProductionOrderItemType type,
								   OccurrenceDTO occurrence,boolean rawMaterial) {
		this.productionOrderId = productionOrderId;
		this.product = product;
		this.serie=serie;
		this.stockId=stockId;
		this.quantity = quantity;
		this.cost = cost;
		this.type = type;
		this.occurrence = occurrence;
		this.rawMaterial=rawMaterial;
	}
	
	public ProductionOrderItemDTO(ProductionOrderItem entity) {
		this.productionOrderId = entity.getProductionOrder().getId();
		this.product = new ProductDTO(entity.getProduct());
		this.serie=entity.getSerie();
		this.stockId=entity.getStockId();
		this.quantity = entity.getQuantity();
		this.cost = entity.getCost();
		this.type = entity.getType();
		this.occurrence = new OccurrenceDTO(entity.getOccurrence());
		this.rawMaterial=entity.getRawMaterial();
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
	public boolean getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(boolean rawMaterial) {
		this.rawMaterial = rawMaterial;
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

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public OccurrenceDTO getOccurrence() {
		return occurrence;
	}

	public void setOccurrence(OccurrenceDTO occurrence) {
		this.occurrence = occurrence;
	}


	
}
