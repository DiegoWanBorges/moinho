package com.twokeys.moinho.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Employee;
import com.twokeys.moinho.entities.LaborCostType;
import com.twokeys.moinho.entities.LaborPayment;

@Repository
public interface LaborPaymentRepository extends JpaRepository<LaborPayment, Long> {

	@Query(value = " SELECT "
				 + "s.id as sector_id, "
				 + "s.name as sector, "
				 + "sum(p.value) as total  "
				 + "FROM LaborPayment lp "
				 + "inner join Payment p ON p.id = lp.id "
				 + "inner join LaborCostType lct ON lct.id = lp.laborCostType.id "
				 + "inner join Employee e ON e.id = lp.employee.id "
				 + "inner join Sector s ON s.id = e.sector.id "
				 + "where p.date between :startDate and :endDate "
				 + "group by "
				 + "s.name, "
				 + "s.id ")
		List<Object[]> listCostLaborGroupBySector(LocalDate startDate,LocalDate endDate);
	
	
	@Query("SELECT obj FROM LaborPayment obj "
			 + "WHERE (COALESCE(:laborCostType) IS NULL OR laborCostType = :laborCostType) "
			 + "AND (COALESCE(:employee) IS NULL OR employee = :employee) "
			 + "AND (obj.date between :startDate and :endDate) ")
	Page<LaborPayment> findByDateAndEmployeeAndLaborCostType(Employee employee,LaborCostType laborCostType ,LocalDate startDate,LocalDate endDate, Pageable pageable);
	
	
		
}

