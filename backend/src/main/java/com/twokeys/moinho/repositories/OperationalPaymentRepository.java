package com.twokeys.moinho.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.OperationalCostType;
import com.twokeys.moinho.entities.OperationalPayment;

@Repository
public interface OperationalPaymentRepository extends JpaRepository<OperationalPayment, Long> {
	@Query(value = " SELECT  "
				 + "at.id, "
				 + "at.name, "
				 + "at.type, "
				 + "sum(p.value) as total "
				 + "FROM Payment p "
				 + "inner join OperationalPayment op  on op.id = p.id "
				 + "inner join OperationalCostType  at ON at.id = op.operationalCostType.id "
				 + "where p.date between :startDate and :endDate "
				 + "group by at.id,at.name,at.type "
				 + "order by total desc ")
	List<Object[]> listOperationalCostGroupByType(LocalDate startDate,LocalDate endDate);
	
	@Query("SELECT obj FROM OperationalPayment obj "
		 + "WHERE (COALESCE(:operationalCostType) IS NULL OR operationalCostType = :operationalCostType) "
		 + "AND (obj.date between :startDate and :endDate) ")
	Page<OperationalPayment> findByDateAndType(OperationalCostType operationalCostType,LocalDate startDate,LocalDate endDate, Pageable pageable);
}

