package com.twokeys.moinho.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.CostCalculation;

@Repository
public interface CostCalculationRepository extends JpaRepository<CostCalculation, Long> {

	@Query("SELECT obj FROM CostCalculation obj "
		 + "WHERE (obj.referenceMonth between :startDate and :endDate) ")
	Page<CostCalculation> findByReferenceMonthAndStatus(LocalDate startDate,LocalDate endDate, Pageable pageable);
	
	@Query("SELECT obj FROM CostCalculation obj "
		 + "WHERE YEAR(obj.referenceMonth)= :year  "
		 + "AND MONTH(obj.referenceMonth)= :month "
		 + "AND obj.status=1 ")
	CostCalculation findByReferenceMonthAndYearAndMonth(Integer year, Integer month);
}

