package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.LaborPayment;

@Repository
public interface LaborPaymentRepository extends JpaRepository<LaborPayment, Long> {

	
		
}

