package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.OperationalCostType;

@Repository
public interface OperationalCostTypeRepository extends JpaRepository<OperationalCostType, Long> {
	Page<OperationalCostType> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<OperationalCostType> findByNameLikeIgnoreCase(String nameConcat);
		
}

