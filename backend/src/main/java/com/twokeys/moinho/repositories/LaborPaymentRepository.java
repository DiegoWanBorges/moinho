package com.twokeys.moinho.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.LaborPayment;

@Repository
public interface LaborPaymentRepository extends JpaRepository<LaborPayment, Long> {

	@Query(value = " SELECT "
			+ "tb_sector.id as sector_id, "
			+ "tb_sector.name as sector, "
			+ "sum(tb_payment.value) as total " 
			+ "FROM tb_labor_payment "
			+ "inner join tb_payment ON tb_payment.id = tb_labor_payment.id "
			+ "inner join tb_labor_cost_type ON tb_labor_cost_type.id = tb_labor_payment.labor_cost_type "
			+ "inner join tb_employee ON tb_employee.id = tb_labor_payment.employee_id "
			+ "inner join tb_sector ON tb_sector.id = tb_employee.sector_id "
			+ "where tb_payment.date between :startDate and :endDate "
			+ "group by tb_sector.name,tb_sector.id " 
		    , nativeQuery = true)
	List<Object[]> listCostLaborGroupBySector(Instant startDate,Instant endDate);
		
}

