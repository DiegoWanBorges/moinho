package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Occurrence;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
	List<Occurrence> findByNameLikeIgnoreCase(String nameConcat);
	Page<Occurrence> findByNameLikeIgnoreCase(String name,Pageable pageable);
}

