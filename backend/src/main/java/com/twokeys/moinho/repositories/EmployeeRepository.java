package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Page<Employee> findByNameLikeIgnoreCase(String name,Pageable pageable);
	List<Employee> findByNameLikeIgnoreCase(String nameConcat);
		
}

