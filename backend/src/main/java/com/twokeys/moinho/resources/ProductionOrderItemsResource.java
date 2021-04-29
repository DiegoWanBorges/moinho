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

import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.services.ProductionOrderItemService;

@RestController
@RequestMapping(value="/productionorderitems")
public class ProductionOrderItemsResource {
	@Autowired
	ProductionOrderItemService service;
	
	@PostMapping
	public ResponseEntity<List<ProductionOrderItemDTO>> insert(@RequestBody List<ProductionOrderItemDTO> dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.get(0).getProductionOrderId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
}

