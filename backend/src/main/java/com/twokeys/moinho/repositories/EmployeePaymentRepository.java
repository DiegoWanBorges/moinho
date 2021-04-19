package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.EmployeePayment;

@Repository
public interface EmployeePaymentRepository extends JpaRepository<EmployeePayment, Long> {

	
		
}

