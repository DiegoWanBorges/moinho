package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProductionOrderProduced;
import com.twokeys.moinho.entities.pk.ProductionOrderProductProducedPK;

@Repository
public interface ProductionOrderProducedRepository extends JpaRepository<ProductionOrderProduced, ProductionOrderProductProducedPK> {
		
}

