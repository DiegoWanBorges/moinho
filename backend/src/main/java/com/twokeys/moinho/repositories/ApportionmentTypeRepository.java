package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ApportionmentType;

@Repository
public interface ApportionmentTypeRepository extends JpaRepository<ApportionmentType, Long> {
	Page<ApportionmentType> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<ApportionmentType> findByNameLikeIgnoreCase(String nameConcat);
		
}

