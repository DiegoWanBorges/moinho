package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.LaborCostType;

@Repository
public interface LaborCostTypeRepository extends JpaRepository<LaborCostType, Long> {

	List<LaborCostType> findByNameLikeIgnoreCase(String nameConcat);
		
}

