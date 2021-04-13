package com.twokeys.moinho.dto;

import java.io.Serializable;

import com.twokeys.moinho.entities.Formulation;

public class FormulationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double coefficient;
	private String description;
	private ProductDTO product;
	
	public  FormulationDTO() {
	}

	public FormulationDTO(Long id, Double coefficient, String description, ProductDTO product) {
		this.id = id;
		this.coefficient = coefficient;
		this.description = description;
		this.product = product;
	}
	public FormulationDTO(Formulation entity) {
		ProductDTO productDTO = new ProductDTO();
		this.id = entity.getId();
		this.coefficient = entity.getCoefficient();
		this.description = entity.getDescription();
		this.product = productDTO.convertToDTO(entity.getProduct());
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}
	
	
}
