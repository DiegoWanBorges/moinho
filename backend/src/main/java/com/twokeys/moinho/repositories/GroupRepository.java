package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Line;

@Repository
public interface GroupRepository extends JpaRepository<Line, Long> {
		
}

