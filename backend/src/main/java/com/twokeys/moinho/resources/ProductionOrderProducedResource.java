package com.twokeys.moinho.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.services.ProductionOrderProducedService;

@RestController
@RequestMapping(value="productionordersproduced")
public class ProductionOrderProducedResource {
	@Autowired
	ProductionOrderProducedService service;
	
	@PostMapping
	public ResponseEntity<ProductionOrderProducedDTO> insert(@RequestBody ProductionOrderProducedDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getProductionOrderId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping
	public ResponseEntity<ProductionOrderProducedDTO> update(@RequestBody ProductionOrderProducedDTO dto){
		dto = service.update(dto);
		return ResponseEntity.ok().body(dto);
	}
	@DeleteMapping
	public ResponseEntity<ProductionOrderProducedDTO> delete(@RequestParam(value = "productionOrderId") Long productionOrderId,
														 	 @RequestParam(value = "pallet") Integer pallet){
		service.delete(productionOrderId, pallet);
		return ResponseEntity.noContent().build(); 
	}
	
}

