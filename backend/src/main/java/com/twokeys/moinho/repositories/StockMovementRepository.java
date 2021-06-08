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
		
		@Query(value = " select "
				 + "obj.product, "
				 + "sum(obj.entry)-sum(obj.out) as balance, "
				 + "(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost))/(sum(obj.entry)-sum(obj.out)) as averageCost "
				 + "from StockMovement obj   "
				 + "where obj.product = :product "
				 + "group by "
				 + "obj.product ")
		List<Object[]> currentStockByProduct(Product product);
		
		@Query(value = " select "
				 + "obj.product, "
				 + "sum(obj.entry)-sum(obj.out) as balance, "
				 + "(sum(obj.entry*obj.cost)-sum(obj.out*obj.cost))/(sum(obj.entry)-sum(obj.out)) as averageCost "
				 + "from StockMovement obj   "
				 + "where obj.product = :product "
				 + "and obj.date < :date "
				 + "group by "
				 + "obj.product ")
		List<Object[]> stockByProductAndDatePrevious(Product product, LocalDate date);
		
		
		
		@Query("SELECT obj FROM StockMovement obj "
			 + "WHERE (COALESCE(:product) IS NULL OR product = :product) "
			 + "AND (obj.date between :startDate and :endDate) ")
		Page<StockMovement> findByStartDateAndProduct(Product product,LocalDate startDate,LocalDate endDate, Pageable pageable);
}

