package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionCost;

@Repository
public interface ProductionCostRepository extends JpaRepository<ProductionCost, Long> {

		
}

