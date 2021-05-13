package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Formulation;

@Repository
public interface FormulationRepository extends JpaRepository<Formulation, Long> {
	Page<Formulation> findByDescriptionLikeIgnoreCase(String name,Pageable pageable);
	List<Formulation> findByDescriptionLikeIgnoreCase(String nameConcat);
}

