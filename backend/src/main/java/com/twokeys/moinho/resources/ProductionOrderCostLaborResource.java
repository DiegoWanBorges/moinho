package com.twokeys.moinho.resources;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twokeys.moinho.services.ProductionOrderCostLaborService;

@RestController
@RequestMapping(value="/productionordercostlabors")
public class ProductionOrderCostLaborResource {
	@Autowired
	ProductionOrderCostLaborService service;
	
	@PostMapping
	public void createProductionOrder(@RequestParam("startdate")  LocalDateTime startDate,
									  @RequestParam("enddate")  LocalDateTime endDate) {
		Instant i1 = startDate.toInstant(ZoneOffset.UTC);
		Instant i2 = endDate.toInstant(ZoneOffset.UTC);
		
		service.LaborPaymentApportionment(i1, i2);
	}
	
	
	
}

