package com.twokeys.moinho.entities;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="operationalPayment")
public class OperationalPayment   extends Payment {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="apportionment_type_id")
	private ApportionmentType apportionmentType;
	
	
	public OperationalPayment() {
	}

	public OperationalPayment(ApportionmentType apportionmentType) {
		this.apportionmentType = apportionmentType;
	}

		public ApportionmentType getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(ApportionmentType apportionmentType) {
		this.apportionmentType = apportionmentType;
	}
	
}
