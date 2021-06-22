package com.twokeys.moinho;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twokeys.moinho.services.StockMovementService;


@SpringBootApplication
public class MoinhoApplication {
	
	@Autowired
	private StockMovementService stockMovementService;
	public static void main(String[] args) {
		
		SpringApplication.run(MoinhoApplication.class, args);
		
	}
	@PostConstruct
	private void init(){
		stockMovementService.updateAverageCost();
	}

}
