package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderOperationalCost;
import com.twokeys.moinho.entities.pk.ProductionOrderOperationalCostPK;

@Repository
public interface ProductionOrderOperationalCostRepository extends JpaRepository<ProductionOrderOperationalCost, ProductionOrderOperationalCostPK> {
   void	deleteByIdProductionOrder(ProductionOrder productionOrder);
   
   @Query("SELECT obj FROM ProductionOrderOperationalCost obj "
		+ "WHERE obj.id.productionOrder.id= :id ")
   List<ProductionOrderOperationalCost> findByIdProductionOrderId (Long id);
}

