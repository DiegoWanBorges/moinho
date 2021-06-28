package com.twokeys.moinho.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;
import com.twokeys.moinho.entities.enums.StockMovementType;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
		List<StockMovement> findByProduct(Product product);
		
		@Query(value = "select "
					 + "obj.product.id, "
					 + "obj.product.name, "
					 + "obj.product.unity.id, "
					 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
					 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
				     + "from StockMovement  obj   "
				     + "where obj.product.id = :productId "
				     + "group by "
					 + "obj.product.id, "
					 + "obj.product.name, "
					 + "obj.product.unity.id ")
		List<Object[]> currentStockByProduct(Long productId);
		
		@Query(value = "select "
				 	 + "obj.product.id, "
				 	 + "obj.product.name, "
				 	 + "obj.product.unity.id, "
					 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
				 	 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
				 	 + "from StockMovement obj   "
				 	 + "where obj.product.id = :productId "
				 	 + "and obj.date <= :date "
				 	 + "group by "
					 + "obj.product.id, "
					 + "obj.product.name, "
					 + "obj.product.unity.id ")
		List<Object[]> stockByProductAndPreviousAndEqualDate(Long productId, LocalDate date);
		
		@Query(value = "select "
			 	 + "obj.product.id, "
			 	 + "obj.product.name, "
			 	 + "obj.product.unity.id, "
				 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
			 	 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
			 	 + "from StockMovement obj   "
			 	 + "where obj.product.id = :productId "
			 	 + "and obj.date < :date "
			 	 + "group by "
				 + "obj.product.id, "
				 + "obj.product.name, "
				 + "obj.product.unity.id ")
		List<Object[]> stockByPreviousDateAndProductId(Long productId, LocalDate date);
		
		@Query(value = "select "
					 + "prod.id, "
					 + "prod.name, "
					 + "prod.unity_id, "
				 	 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
			 	     + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
			 	     + "from tb_product prod "
			 	     + "LEFT JOIN tb_stock_movement obj on obj.product_id = prod.id   "
			 	     + "where obj.date <= :date "
			 	     + "group by  "
			 	     + "prod.id, "
					 + "prod.name, "
					 + "prod.unity_id ",nativeQuery = true)
		List<Object[]> stockByPreviousAndEqualDate(LocalDate date);
		
		
		@Query(value = "select "
					 + "obj.product.id, "
					 + "obj.product.name, "
					 + "obj.product.unity.id, "
					 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
					 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
					 + "from StockMovement obj   "
					 + "where obj.date BETWEEN :startDate and :endDate "
					 + "and obj.type = :type "
					 + "group by "
					 + "obj.product.id, "
					 + "obj.product.name, "
					 + "obj.product.unity.id ")
		List<Object[]> stockByDateBetweenAndType(LocalDate startDate,LocalDate endDate, StockMovementType type);
		
		@Query(value = "select "
				 + "obj.product.id, "
				 + "obj.product.name, "
				 + "obj.product.unity.id, "
				 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
				 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance, "
				 + "coalesce(sum(obj.entry),0) as totalEntry, "
				 + "coalesce(sum(obj.out),0) as totalOut "
				 + "from StockMovement obj   "
				 + "where obj.date BETWEEN :startDate and :endDate "
				 + "and obj.product.id = :productId "
				 + "group by "
				 + "obj.product.id, "
				 + "obj.product.name, "
				 + "obj.product.unity.id ")
		List<Object[]> stockByDateBetweenAndProductId(LocalDate startDate,LocalDate endDate, Long productId);
		
		@Query(value = "select "
				 + "obj.product.id, "
				 + "obj.product.name, "
				 + "obj.product.unity.id, "
				 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
				 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
				 + "from StockMovement obj   "
				 + "group by "
				 + "obj.product.id, "
				 + "obj.product.name, "
				 + "obj.product.unity.id ")
		List<Object[]> stockByCurrentAverageCost();
				
		
		@Query("SELECT obj FROM StockMovement obj "
			 + "WHERE (COALESCE(:product) IS NULL OR product = :product) "
			 + "AND (obj.date between :startDate and :endDate) ")
		Page<StockMovement> findByStartDateAndProduct(Product product,LocalDate startDate,LocalDate endDate, Pageable pageable);
}

