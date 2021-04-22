package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	List<Group> findByNameLikeIgnoreCase(String nameConcat);
		
}

