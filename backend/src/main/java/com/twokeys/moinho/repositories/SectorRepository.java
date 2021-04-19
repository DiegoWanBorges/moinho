package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

	List<Sector> findByNameLikeIgnoreCase(String nameConcat);
		
}

