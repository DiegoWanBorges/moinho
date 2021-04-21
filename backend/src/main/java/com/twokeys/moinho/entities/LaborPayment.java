package com.twokeys.moinho.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="laborPayment")
public class LaborPayment  extends Payment {
	private static final long serialVersionUID = 1L;
		
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@ManyToOne
	@JoinColumn(name="laborCostType")
	private LaborCostType laborCostType;
	
	public LaborPayment() {
	}

	public LaborPayment(Employee employee, LaborCostType laborCostType) {
		super();
		this.employee = employee;
		this.laborCostType = laborCostType;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LaborCostType getLaborCostType() {
		return laborCostType;
	}

	public void setLaborCostType(LaborCostType laborCostType) {
		this.laborCostType = laborCostType;
	}

	
	
}