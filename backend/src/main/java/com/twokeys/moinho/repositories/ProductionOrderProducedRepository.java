package com.twokeys.moinho.repositories;

import java.time.Instant;
import java.util.List;

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
	
	@Query(value = "select "
			 	 + "obj.product.id, "
			 	 + "obj.product.name, "
			 	 + "obj.product.unity.id, "
			 	 + "sum(obj.quantity) as totalProduced,  "
			 	 + "sum(obj.quantity*obj.unitCost)/sum(obj.quantity) as averegeCost "
			 	 + "from ProductionOrderProduced  obj   "
			 	 + "where obj.id.productionOrder.startDate between :startDate and :endDate  " 
			 	 + "AND obj.product.id = :productId "
			 	 + "GROUP BY "
			 	 + "obj.product.id, "
			 	 + "obj.product.name, "
			 	 + "obj.product.unity.id ")
	List<Object[]> findProducedByProductAndStartDate(Long productId, Instant startDate, Instant endDate);
	
	@Query(value = "select "
		 	 + "obj.product.id, "
		 	 + "obj.product.name, "
		 	 + "obj.product.unity.id, "
		 	 + "sum(obj.quantity) as totalProduced,  "
		 	 + "sum(obj.quantity*obj.unitCost)/sum(obj.quantity) as averegeCost "
		 	 + "from ProductionOrderProduced  obj   "
		 	 + "where obj.id.productionOrder.startDate between :startDate and :endDate  " 
		 	 + "GROUP BY "
		 	 + "obj.product.id, "
		 	 + "obj.product.name, "
		 	 + "obj.product.unity.id ")
	List<Object[]> findProducedByStartDate(Instant startDate, Instant endDate);
	
}

