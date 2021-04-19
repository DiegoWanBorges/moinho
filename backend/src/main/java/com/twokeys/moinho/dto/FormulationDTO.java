package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.twokeys.moinho.entities.Formulation;

public class FormulationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Double coefficient;
	private String description;
	private ProductDTO product;
	private SectorDTO sector;
	private List<ProductionApportionmentDTO> apportionments = new ArrayList<>();
	private List<FormulationItemsDTO> formulationItems = new ArrayList<>();
	
	public  FormulationDTO() {
	}

	public FormulationDTO(Long id, Double coefficient, String description, ProductDTO product,SectorDTO sector) {
		this.id = id;
		this.coefficient = coefficient;
		this.description = description;
		this.product = product;
		this.sector = sector;
	}
	public FormulationDTO(Formulation entity) {
		this.id = entity.getId();
		this.coefficient = entity.getCoefficient();
		this.description = entity.getDescription();
		this.product = new ProductDTO(entity.getProduct());
		this.sector = new SectorDTO(entity.getSector());
		entity.getProductionApportionments().forEach(apportionment -> apportionments.add(new ProductionApportionmentDTO(apportionment)));
		entity.getFormulationItems().forEach(formulation -> formulationItems.add(new FormulationItemsDTO(formulation)));
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
	
	public SectorDTO getSector() {
		return sector;
	}

	public void setSector(SectorDTO sector) {
		this.sector = sector;
	}

	public List<ProductionApportionmentDTO> getApportionments() {
		return apportionments;
	}

	public List<FormulationItemsDTO> getFormulationItems() {
		return formulationItems;
	}
	
	
}
