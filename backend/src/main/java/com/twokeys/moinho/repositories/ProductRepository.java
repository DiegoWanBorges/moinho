package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
		
}

