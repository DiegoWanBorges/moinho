package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
		List<StockMovement> findByProduct(Product product);
		
		@Query(value = " select "
					 + "product_id, "
					 + "sum(entry) as entry, "
					 + "sum(out) as out, "
					 + "sum(entry)-sum(out) as balance, "
					 + "(sum(entry*cost)-sum(out*cost))/(sum(entry)-sum(out)) as average "
					 + "from tb_stock_movement "
					 + "where product_id = :product_id "
					 + "group by "
					 + "product_id " 
				, 	nativeQuery = true)
		List<Object[]> stockBalance(Long product_id);
}

