package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderCostLabor;

@Repository
public interface ProductionOrderCostLaborRepository extends JpaRepository<ProductionOrderCostLabor, Long> {
   void	deleteByIdProductionOrder(ProductionOrder productionOrder);
}

