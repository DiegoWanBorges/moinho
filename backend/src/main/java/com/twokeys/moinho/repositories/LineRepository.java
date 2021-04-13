package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Line;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {

	List<Line> findByNameLikeIgnoreCase(String nameConcat);
		
}

