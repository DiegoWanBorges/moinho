package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.pk.ProductionOrderProducedPK;

@Repository
public interface ProductionOrderProducedRepository extends JpaRepository<ProductionOrderProduced, ProductionOrderProducedPK> {
	@Query("SELECT COALESCE(MAX(obj.id.pallet),0) FROM ProductionOrderProduced obj where obj.id.productionOrder = :order")
	Integer findMaxPallet(ProductionOrder order);	
}

