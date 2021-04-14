package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Product;
import com.twokeys.moinho.entities.StockMovement;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
		List<StockMovement> findByProduct(Product product);
}

