package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrder;
import com.twokeys.moinho.entities.ProductionOrderCostLabor;
import com.twokeys.moinho.entities.pk.ProductionOrderCostLaborPK;

@Repository
public interface ProductionOrderCostLaborRepository extends JpaRepository<ProductionOrderCostLabor, ProductionOrderCostLaborPK> {
   void	deleteByIdProductionOrder(ProductionOrder productionOrder);
   
   @Query("SELECT obj FROM ProductionOrderCostLabor obj "
		+ "WHERE obj.id.productionOrder.id= :id ")
	   List<ProductionOrderCostLabor> findByIdProductionOrderId (Long id);
}

