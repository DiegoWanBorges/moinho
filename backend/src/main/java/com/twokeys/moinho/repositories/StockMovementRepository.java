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

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
		List<StockMovement> findByProduct(Product product);
		
		@Query(value = "select "
					 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
					 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
				     + "from StockMovement  obj   "
				     + "where obj.product.id = :productId ")
		List<Object[]> currentStockByProduct(Long productId);
		
		@Query(value = "select "
					 + "coalesce(sum(obj.entry)-sum(obj.out),0) as balance, "
				 	 + "coalesce(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost),0) as financialStockBalance "
				 	 + "from StockMovement obj   "
				 	 + "where obj.product = :product "
				 	 + "and obj.date <= :date ")
		List<Object[]> stockByProductAndPreviousAndEqualDate(Product product, LocalDate date);
		
		
		
		@Query("SELECT obj FROM StockMovement obj "
			 + "WHERE (COALESCE(:product) IS NULL OR product = :product) "
			 + "AND (obj.date between :startDate and :endDate) ")
		Page<StockMovement> findByStartDateAndProduct(Product product,LocalDate startDate,LocalDate endDate, Pageable pageable);
}

