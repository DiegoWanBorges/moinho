package com.twokeys.moinho.repositories;

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
		 + "and obj.rawMaterial=true")
	Double findTotalRawMaterial(ProductionOrder order);
	
}

