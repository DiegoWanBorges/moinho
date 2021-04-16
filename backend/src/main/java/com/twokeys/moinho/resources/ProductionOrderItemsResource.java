package com.twokeys.moinho.resources;

import java.net.URI;

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
	public ResponseEntity<ProductionOrderItemsDTO> insert(@RequestBody ProductionOrderItemsDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getProduct().getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
}

