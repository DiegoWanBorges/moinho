package com.twokeys.moinho.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.twokeys.moinho.entities.enums.FormulationType;

@Entity
@Table(name="tb_formulation")
public class Formulation   implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double coefficient;
	private String description;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	private FormulationType type;
	private Integer level;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tb_formulation_operational_cost", 
			joinColumns = @JoinColumn(name="formulation_id"),
			inverseJoinColumns = @JoinColumn(name="operational_cost_type_id")
			)
	private Set<OperationalCostType> operationalCostType = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tb_formulation_sector", 
			   joinColumns = @JoinColumn(name="formulation_id"),
			   inverseJoinColumns = @JoinColumn(name="sector_id")
			)
	private Set<Sector> sectors = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="tb_formulation_secondary_production", 
			   joinColumns = @JoinColumn(name="formulation_id"),
			   inverseJoinColumns = @JoinColumn(name="product_id")
			)
	private Set<Product> secondaryProduction = new HashSet<>();
	
	@OneToMany(mappedBy = "id.formulation")
	private List<FormulationItem> formulationItems = new ArrayList<>();
		
	public  Formulation() {
	}
	
	public Formulation(Long id, Double coefficient, String description,Product product, 
					   FormulationType type, Integer level) {
		this.id = id;
		this.coefficient = coefficient;
		this.description = description;
		this.product = product;
		this.type=type;
		this.level=level;
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
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
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

	public Set<OperationalCostType> getOperationalCostType() {
		return operationalCostType;
	}
	public Set<Sector> getSectors() {
		return sectors;
	}
	public List<FormulationItem> getFormulationItems() {
		return formulationItems;
	}
	
	public Set<Product> getSecondaryProduction() {
		return secondaryProduction;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formulation other = (Formulation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
