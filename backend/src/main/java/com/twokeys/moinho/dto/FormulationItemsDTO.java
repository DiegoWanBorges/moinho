package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.FormulationItems;
import com.twokeys.moinho.entities.enums.FormulationItemsType;

public class FormulationItemsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long formulationId;
	private Double quantity;
	private Integer round;
	private FormulationItemsType type;
	private ProductDTO product;
	public FormulationItemsDTO() {
	}

	public FormulationItemsDTO(Long formulationId, ProductDTO product, Double quantity, Integer round,
							   FormulationItemsType type) {
		this.formulationId = formulationId;
		this.product = product;
		this.quantity = quantity;
		this.round = round;
		this.type = type;
	}
	
	public FormulationItemsDTO(FormulationItems entity) {
		ProductDTO prodDTO = new ProductDTO();
		prodDTO = prodDTO.convertToDTO(entity.getProduct());
		this.formulationId = entity.getFormulation().getId();
		this.product = prodDTO;
		this.quantity = entity.getQuantity();
		this.round = entity.getRound();
		this.type = entity.getType();
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

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public FormulationItemsType getType() {
		return type;
	}

	public void setType(FormulationItemsType type) {
		this.type = type;
	}
	
	
}
