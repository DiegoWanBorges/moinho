package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Parameter;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {
;
		
}

