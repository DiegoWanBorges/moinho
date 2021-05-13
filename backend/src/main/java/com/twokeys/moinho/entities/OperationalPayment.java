package com.twokeys.moinho.entities;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_operational_payment")
public class OperationalPayment   extends Payment {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="apportionment_type_id")
	private OperationalCostType apportionmentType;
	
	
	public OperationalPayment() {
	}

	public OperationalPayment(OperationalCostType apportionmentType) {
		this.apportionmentType = apportionmentType;
	}

		public OperationalCostType getApportionmentType() {
		return apportionmentType;
	}

	public void setApportionmentType(OperationalCostType apportionmentType) {
		this.apportionmentType = apportionmentType;
	}
	
}
