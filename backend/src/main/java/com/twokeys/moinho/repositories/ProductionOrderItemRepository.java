package com.twokeys.moinho.repositories;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItem;
import com.twokeys.moinho.entities.pk.ProductionOrderItemPK;

@Repository
public interface ProductionOrderItemRepository extends JpaRepository<ProductionOrderItem, ProductionOrderItemPK> {
	 
		@Query("SELECT COALESCE(MAX(obj.id.serie),0) FROM ProductionOrderItem obj where obj.id.productionOrder = :order")
	Integer findMaxSerie(ProductionOrder order);
	
		@Query("SELECT "
			 + "SUM(obj.quantity * prod.rawMaterialConversion) "
			 + "FROM ProductionOrderItem obj "
			 + "INNER JOIN obj.id.product prod "
			 + "where obj.id.productionOrder = :order "
			 + "and obj.rawMaterial=true "
			 + "and obj.id.productionOrder.dateCancel is null")
	Double findTotalRawMaterial(ProductionOrder order);
	
		@Query("SELECT obj FROM ProductionOrderItem obj "
			 + "where obj.id.productionOrder.startDate between :startDate and :endDate "
			 + "AND obj.id.product.id= :productId " 
			 + "and obj.id.productionOrder.dateCancel is null")
		List<ProductionOrderItem> findByDateAndProduct(Instant startDate,Instant endDate, Long productId);
		
		@Query("SELECT obj FROM ProductionOrderItem obj "
				 + "where obj.id.productionOrder.startDate between :startDate and :endDate " 
				 + "and obj.id.productionOrder.dateCancel is null")
		List<ProductionOrderItem> findByDate(Instant startDate,Instant endDate);
	
}

