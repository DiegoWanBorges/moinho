package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrderItems;
import com.twokeys.moinho.entities.pk.ProductionOrderItemsPK;

@Repository
public interface ProductionOrderItemRepository extends JpaRepository<ProductionOrderItems, ProductionOrderItemsPK> {
		
}

