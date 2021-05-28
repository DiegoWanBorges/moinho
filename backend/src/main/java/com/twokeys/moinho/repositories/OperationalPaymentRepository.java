package com.twokeys.moinho.repositories;

import java.time.Instant;
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
				 + "FROM tb_payment p "
				 + "inner join tb_operational_payment op  on op.id = p.id "
				 + "inner join tb_apportionment_type at ON at.id = op.apportionment_type_id "
				 + "where p.date between :startDate and :endDate "
				 + "group by at.id,at.name,at.type "
				 + "order by total desc " 
		    , nativeQuery = true)
	List<Object[]> listOperationalCostGroupByTypeApportionment(Instant startDate,Instant endDate);
	
	@Query("SELECT obj FROM OperationalPayment obj "
		 + "WHERE (COALESCE(:operationalCostType) IS NULL OR operationalCostType = :operationalCostType) "
		 + "AND (obj.date between :startDate and :endDate) ")
	Page<OperationalPayment> findByDateAndType(OperationalCostType operationalCostType,LocalDate startDate,LocalDate endDate, Pageable pageable);
}

