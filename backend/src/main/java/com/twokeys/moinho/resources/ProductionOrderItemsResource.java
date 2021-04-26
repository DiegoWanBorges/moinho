package com.twokeys.moinho.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.ProductionOrderItemsDTO;
import com.twokeys.moinho.services.ProductionOrderItemsService;

@RestController
@RequestMapping(value="/productionorderitems")
public class ProductionOrderItemsResource {
	@Autowired
	ProductionOrderItemsService service;
	
	@PostMapping
	public ResponseEntity<List<ProductionOrderItemsDTO>> insert(@RequestBody List<ProductionOrderItemsDTO> dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.get(0).getProductionOrderId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
}
