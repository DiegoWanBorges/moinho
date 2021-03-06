package com.twokeys.moinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
		
}

