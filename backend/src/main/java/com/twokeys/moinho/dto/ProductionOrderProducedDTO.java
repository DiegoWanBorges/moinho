package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twokeys.moinho.entities.ProductionOrderProduced;

public class ProductionOrderProducedDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long productionOrderId;
	private Integer pallet;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone="GMT-3")
	private Instant manufacturingDate;
	private String lote;
	private Double quantity;
	private ProductDTO product;
	private PalletStatusDTO palletStatus;
	private Long stockId;
	private Double unitCost = 0.0;
	
	public ProductionOrderProducedDTO(){	
	}

	public ProductionOrderProducedDTO(Long productionOrderId, Integer pallet, Instant manufacturingDate, String lote,
									  Double quantity, ProductDTO product,PalletStatusDTO palletStatus,
									  Long stockId, Double unitCost) {
		this.productionOrderId = productionOrderId;
		this.pallet = pallet;
		this.manufacturingDate = manufacturingDate;
		this.lote = lote;
		this.quantity = quantity;
		this.product = product;
		this.palletStatus=palletStatus;
		this.stockId=stockId;
		this.unitCost=unitCost;
	}
	public ProductionOrderProducedDTO(ProductionOrderProduced entity) {
		this.productionOrderId = entity.getProductionOrder().getId();
		this.pallet = entity.getPallet();
		this.manufacturingDate = entity.getManufacturingDate();
		this.lote = entity.getLote();
		this.quantity = entity.getQuantity();
		this.product = new ProductDTO(entity.getProduct());
		this.palletStatus = new PalletStatusDTO(entity.getPalletStatus());
		this.stockId=entity.getStockId();
		this.unitCost=entity.getUnitCost();
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

	public PalletStatusDTO getPalletStatus() {
		return palletStatus;
	}

	public void setPalletstatus(PalletStatusDTO palletStatus) {
		this.palletStatus = palletStatus;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}
	
	
}
