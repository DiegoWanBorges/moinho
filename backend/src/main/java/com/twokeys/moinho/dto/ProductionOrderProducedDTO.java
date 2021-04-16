package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.ProductionOrderProduced;

public class ProductionOrderProducedDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long productionOrderId;
	private Integer pallet;
	private Instant manufacturingDate;
	private String lote;
	private Double quantity;
	private ProductDTO product;
	
	public ProductionOrderProducedDTO(){	
	}

	public ProductionOrderProducedDTO(Long productionOrderId, Integer pallet, Instant manufacturingDate, String lote,
									  Double quantity, ProductDTO product) {
		this.productionOrderId = productionOrderId;
		this.pallet = pallet;
		this.manufacturingDate = manufacturingDate;
		this.lote = lote;
		this.quantity = quantity;
		this.product = product;
	}
	public ProductionOrderProducedDTO(ProductionOrderProduced entity) {
		this.productionOrderId = entity.getProductionOrder().getId();
		this.pallet = entity.getPallet();
		this.manufacturingDate = entity.getManufacturingDate();
		this.lote = entity.getLote();
		this.quantity = entity.getQuantity();
		this.product = new ProductDTO(entity.getProduct());
	}

	public Long getProductionOrderId() {
		return productionOrderId;
	}

	public void setProductionOrderId(Long productionOrderId) {
		this.productionOrderId = productionOrderId;
	}

	public Integer getPallet() {
		return pallet;
	}

	public void setPallet(Integer pallet) {
		this.pallet = pallet;
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

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	
}
