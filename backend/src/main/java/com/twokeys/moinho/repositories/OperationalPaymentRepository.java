package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.OperationalPayment;

@Repository
public interface OperationalPaymentRepository extends JpaRepository<OperationalPayment, Long> {

		
}

