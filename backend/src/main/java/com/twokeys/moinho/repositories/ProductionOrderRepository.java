package com.twokeys.moinho.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {
	List<ProductionOrder> findByStartDateBetweenAndStatus(Instant startDate, Instant endDate,Integer status);
	List<ProductionOrder> findByEmissionBetweenAndStatus(Instant startDate, Instant endDate,Integer status);
	
	@Query(value = " select  "
				+ " *  "
				+ "from public.tb_production_order po "
				+ "where po.start_date between :startDate and :endDate "
				+ "and po.status=1 "
				+ "and po.formulation_id in( "
				+ "select "
				+ "tb_formulation_sector.formulation_id "
				+ "from public.tb_formulation_sector "
				+ "where tb_formulation_sector.sector_id = :sector_id) " 
				, nativeQuery = true)
	List<ProductionOrder> listProductionOrderByStartDateAndFormulationSector(Instant startDate,Instant endDate, Long sector_id);
	
	@Query(value = " select  "
			+ " *  "
			+ "from public.tb_production_order po "
			+ "where po.start_date between :startDate and :endDate "
			+ "and po.status=1 "
			+ "and po.formulation_id in( "
			+ "select "
			+ "formulation_id "
			+ "from tb_formulation_apportionment "
			+ "where apportionment_type_id = :apportionment_id) " 
			, nativeQuery = true)
	List<ProductionOrder> listProductionOrderByStartDateAndFormulationApportionment(Instant startDate,Instant endDate, Long apportionment_id);
	
}

