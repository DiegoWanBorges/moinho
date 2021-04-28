package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.entities.pk.ProductionOrderItemsPK;

@Repository
public interface ProductionOrderItemsRepository extends JpaRepository<ProductionOrderItems, ProductionOrderItemsPK> {
	 
	@Query("SELECT COALESCE(MAX(obj.id.serie),0) FROM ProductionOrderItems obj where obj.id.productionOrder = :order")
	Integer findMaxSerie(ProductionOrder order);
	
	@Query("SELECT SUM(obj.quantity) FROM ProductionOrderItems obj "
		 + " where obj.id.productionOrder = :order "
		 + " and obj.rawMaterial=1")
	Double findTotalRawMaterial(ProductionOrder order);
	
}

