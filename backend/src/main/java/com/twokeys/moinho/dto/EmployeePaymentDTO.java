package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.EmployeePayment;

public class EmployeePaymentDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		private Long id;
		private Instant payDay;
		private EmployeeDTO employee;
		private TypePaymentEmployeeDTO typePaymentEmployee;
		private Double paymentAmount;
		
		public EmployeePaymentDTO() {
		}

		public EmployeePaymentDTO(Long id, Instant payDay, EmployeeDTO employee,
								  TypePaymentEmployeeDTO typePaymentEmployee, Double paymentAmount) {
			this.id = id;
			this.payDay = payDay;
			this.employee = employee;
			this.typePaymentEmployee = typePaymentEmployee;
			this.paymentAmount = paymentAmount;
		}
		public EmployeePaymentDTO(EmployeePayment entity) {
			this.id = entity.getId();
			this.payDay = entity.getPayDay();
			this.employee = new EmployeeDTO(entity.getEmployee());
			this.typePaymentEmployee = new TypePaymentEmployeeDTO(entity.getTypePaymentEmployee());
			this.paymentAmount = entity.getPaymentAmount();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Instant getPayDay() {
			return payDay;
		}

		public void setPayDay(Instant payDay) {
			this.payDay = payDay;
		}

		public EmployeeDTO getEmployee() {
			return employee;
		}

		public void setEmployee(EmployeeDTO employee) {
			this.employee = employee;
		}

		public TypePaymentEmployeeDTO getTypePaymentEmployee() {
			return typePaymentEmployee;
		}

		public void setTypePaymentEmployee(TypePaymentEmployeeDTO typePaymentEmployee) {
			this.typePaymentEmployee = typePaymentEmployee;
		}

		public Double getPaymentAmount() {
			return paymentAmount;
		}

		public void setPaymentAmount(Double paymentAmount) {
			this.paymentAmount = paymentAmount;
		}		
}
