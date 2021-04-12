package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Unity;

@Repository
public interface UnityRepository extends JpaRepository<Unity, String> {
		
}

