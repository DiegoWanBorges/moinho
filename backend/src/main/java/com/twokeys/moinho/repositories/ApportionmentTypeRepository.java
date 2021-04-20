package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ApportionmentType;

@Repository
public interface ApportionmentTypeRepository extends JpaRepository<ApportionmentType, Long> {

	List<ApportionmentType> findByNameLikeIgnoreCase(String nameConcat);
		
}

