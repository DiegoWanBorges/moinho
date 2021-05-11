package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
	Page<Sector> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<Sector> findByNameLikeIgnoreCase(String nameConcat);
		
}

