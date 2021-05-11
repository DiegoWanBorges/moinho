package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Unity;

@Repository
public interface UnityRepository extends JpaRepository<Unity, String> {
	Page<Unity> findByDescriptionLikeIgnoreCase(String name,Pageable pageable);
	List<Unity> findByDescriptionLikeIgnoreCase(String nameConcat);
}

