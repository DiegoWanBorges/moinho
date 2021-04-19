package com.twokeys.moinho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.twokeys.moinho.entities.EmployeePaymentType;

@Repository
public interface EmployeePaymentTypeRepository extends JpaRepository<EmployeePaymentType, Long> {

	List<EmployeePaymentType> findByNameLikeIgnoreCase(String nameConcat);
		
}

