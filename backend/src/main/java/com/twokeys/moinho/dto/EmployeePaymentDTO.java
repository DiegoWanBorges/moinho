package com.twokeys.moinho.dto;

import java.io.Serializable;
import java.time.Instant;

import com.twokeys.moinho.entities.EmployeePayment;

public class EmployeePaymentDTO implements Serializable {
		private static final long serialVersionUID = 1L;
		
		
		private Long id;
		private Instant date;
		private EmployeeDTO employee;
		private TypePaymentEmployeeDTO typePaymentEmployee;
		private Double paymentAmount;
		
		public EmployeePaymentDTO() {
		}

		public EmployeePaymentDTO(Long id, Instant date, EmployeeDTO employee,
								  TypePaymentEmployeeDTO typePaymentEmployee, Double paymentAmount) {
			this.id = id;
			this.date = date;
			this.employee = employee;
			this.typePaymentEmployee = typePaymentEmployee;
			this.paymentAmount = paymentAmount;
		}
		public EmployeePaymentDTO(EmployeePayment entity) {
			this.id = entity.getId();
			this.date = entity.getDate();
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

		public Instant getDate() {
			return date;
		}

		public void setDate(Instant date) {
			this.date = date;
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
