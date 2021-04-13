package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Formulation;

@Repository
public interface FormulationRepository extends JpaRepository<Formulation, Long> {
	List<Formulation> findByDescriptionLikeIgnoreCase(String nameConcat);
}

