package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Occurrence;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> {
		
}

