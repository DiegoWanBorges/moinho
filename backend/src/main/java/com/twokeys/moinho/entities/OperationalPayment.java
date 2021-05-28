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
	private OperationalCostType operationalCostType;
	
	
	public OperationalPayment() {
	}

	public OperationalPayment(OperationalCostType operationalCostType) {
		this.operationalCostType = operationalCostType;
	}

	public OperationalCostType getOperationalCostType() {
		return operationalCostType;
	}

	public void setOperationalCostType(OperationalCostType operationalCostType) {
		this.operationalCostType = operationalCostType;
	}
	
}
