package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.twokeys.moinho.entities.Formulation;
import com.twokeys.moinho.entities.enums.FormulationType;

public class FormulationDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotNull(message = "Não pode ser nulo")
	@Min(value=1, message="O Valor deve ser maior que zero")
	private Double coefficient;
	
	@NotNull(message = "Não pode ser nulo")
	@NotBlank(message = "Não pode estar em branco")
	@Length(min=2,max=100, message = "Deve possuir entre 2 e 100 caracteres")
	private String description;
	private ProductDTO product;
	private FormulationType type;
	private Integer level;
	private List<OperationalCostTypeDTO> operationalCostType = new ArrayList<>();
	private List<SectorDTO> sectors = new ArrayList<>();
	private List<FormulationItemDTO> formulationItems = new ArrayList<>();
	private List<ProductDTO> secondaryProduction = new ArrayList<>();
	
	public  FormulationDTO() {
	}

	public FormulationDTO(Long id, Double coefficient, String description, ProductDTO product,
						  FormulationType type,Integer level) {
		this.id = id;
		this.coefficient = coefficient;
		this.description = description;
		this.product = product;
		this.type=type;
		this.level=level;
	}
	public FormulationDTO(Formulation entity) {
		this.id = entity.getId();
		this.coefficient = entity.getCoefficient();
		this.description = entity.getDescription();
		this.product = new ProductDTO(entity.getProduct());
		this.type=entity.getType();
		this.level=entity.getLevel();
		entity.getSectors().forEach(sector -> sectors.add(new SectorDTO(sector)));
		entity.getSecondaryProduction().forEach(secondary -> secondaryProduction.add(new ProductDTO(secondary)));
		entity.getOperationalCostType().forEach(e -> operationalCostType.add(new OperationalCostTypeDTO(e)));
		entity.getFormulationItems().forEach(formulation -> formulationItems.add(new FormulationItemDTO(formulation)));
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
	
	public FormulationType getType() {
		return type;
	}

	public void setType(FormulationType type) {
		this.type = type;
	}
		
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<SectorDTO> getSectors() {
		return sectors;
	}

	public List<OperationalCostTypeDTO> getOperationalCostType() {
		return operationalCostType;
	}

	public List<FormulationItemDTO> getFormulationItems() {
		return formulationItems;
	}

	public List<ProductDTO> getSecondaryProduction() {
		return secondaryProduction;
	}
	
}
