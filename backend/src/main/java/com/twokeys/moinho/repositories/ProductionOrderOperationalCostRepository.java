package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderOperationalCost;

@Repository
public interface ProductionOrderOperationalCostRepository extends JpaRepository<ProductionOrderOperationalCost, Long> {
   void	deleteByIdProductionOrder(ProductionOrder productionOrder);
}

