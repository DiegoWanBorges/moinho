package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
	Page<Group> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<Group> findByNameLikeIgnoreCase(String nameConcat);
		
}

