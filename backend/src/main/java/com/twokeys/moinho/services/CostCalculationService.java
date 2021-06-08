package com.twokeys.moinho.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twokeys.moinho.repositories.CostCalculationRepository;

@Service
public class CostCalculationService {
	@Autowired
	private CostCalculationRepository repository;
	
	
	
}
