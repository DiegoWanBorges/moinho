package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.FormulationItem;
import com.twokeys.moinho.entities.enums.FormulationItemType;

public class FormulationItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long formulationId;
	private Double quantity;
	private boolean round;
	private boolean rawMaterial;
	private FormulationItemType type;
	private ProductDTO product;

	public FormulationItemDTO() {
	}

	public FormulationItemDTO(Long formulationId, ProductDTO product, Double quantity, boolean round,
							   FormulationItemType type,boolean rawMaterial) {
		this.formulationId = formulationId;
		this.product = product;
		this.quantity = quantity;
		this.round = round;
		this.rawMaterial=rawMaterial;
		this.type = type;
	}
	
	public FormulationItemDTO(FormulationItem entity) {
		this.formulationId = entity.getFormulation().getId();
		this.product = new ProductDTO(entity.getProduct());
		this.quantity = entity.getQuantity();
		this.round = entity.getRound();
		this.type = entity.getType();
		this.rawMaterial=entity.getRawMaterial();
	}

	public Long getFormulationId() {
		return formulationId;
	}

	public void setFormulationId(Long formulationId) {
		this.formulationId = formulationId;
	}

	

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public boolean getRound() {
		return round;
	}

	public void setRound(boolean round) {
		this.round = round;
	}

	public FormulationItemType getType() {
		return type;
	}

	public void setType(FormulationItemType type) {
		this.type = type;
	}

	public boolean getRawMaterial() {
		return rawMaterial;
	}

	public void setRawMaterial(boolean rawMaterial) {
		this.rawMaterial = rawMaterial;
	}
	
}
