package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.CostCalculation;

@Repository
public interface CostCalculationRepository extends JpaRepository<CostCalculation, Long> {
//	Page<CostCalculation> findByNameLikeIgnoreCase(String name,Pageable pageable);
//	List<CostCalculation> findByNameLikeIgnoreCase(String nameConcat);
		
}

