package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.ProducedProductStatus;

@Repository
public interface ProducedProductStatusRepository extends JpaRepository<ProducedProductStatus, Long> {
	
	Page<ProducedProductStatus> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<ProducedProductStatus> findByNameLikeIgnoreCase(String nameConcat);
		
}

