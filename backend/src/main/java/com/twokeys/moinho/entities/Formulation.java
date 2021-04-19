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

@Entity
@Table(name="formulation")
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
	
	@ManyToOne
	@JoinColumn(name="sector_id")
	private Sector sector;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="formulation_apportionment", 
			joinColumns = @JoinColumn(name="formulation_id"),
			inverseJoinColumns = @JoinColumn(name="formulation_apportionment_type_id")
			)
	private Set<FormulationApportionmentType> productionApportionments = new HashSet<>();
	
	@OneToMany(mappedBy = "id.formulation")
	private List<FormulationItems> formulationItems = new ArrayList<>();
		
	public  Formulation() {
	}
	public Formulation(Long id, Double coefficient, String description,Sector sector, Product product) {
		this.id = id;
		this.coefficient = coefficient;
		this.description = description;
		this.sector=sector;
		this.product = product;
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
	
	public Sector getSector() {
		return sector;
	}
	public void setSector(Sector sector) {
		this.sector = sector;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Set<FormulationApportionmentType> getProductionApportionments() {
		return productionApportionments;
	}
	public List<FormulationItems> getFormulationItems() {
		return formulationItems;
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
