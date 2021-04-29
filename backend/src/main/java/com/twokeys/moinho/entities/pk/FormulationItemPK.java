package com.twokeys.moinho.entities.pk;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.twokeys.moinho.entities.Formulation;
import com.twokeys.moinho.entities.Product;

@Embeddable
public class FormulationItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
		
	@ManyToOne
	@JoinColumn(name="formulation_id")
	private Formulation formulation;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public FormulationItemPK() {
	}

	public FormulationItemPK(Formulation formulation, Product product) {
		this.formulation = formulation;
		this.product = product;
	}
	
	public Formulation getFormulation() {
		return formulation;
	}

	public void setFormulation(Formulation formulation) {
		this.formulation = formulation;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formulation == null) ? 0 : formulation.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		FormulationItemPK other = (FormulationItemPK) obj;
		if (formulation == null) {
			if (other.formulation != null)
				return false;
		} else if (!formulation.equals(other.formulation))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
}
