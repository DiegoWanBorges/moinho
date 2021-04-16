package com.twokeys.moinho.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.ProductionOrderDTO;
import com.twokeys.moinho.services.ProductionOrderService;

@RestController
@RequestMapping(value="/productionorders")
public class ProductionOrderResource {
	@Autowired
	ProductionOrderService service;
	
	@PostMapping
	public ResponseEntity<ProductionOrderDTO> createProductionOrder(@RequestParam(value = "formulationId") Long  formulationId,
													   				@RequestParam(value = "ammount") Double ammount,
													   				@RequestParam(value = "persistence", defaultValue = "false") Boolean persistence){
		ProductionOrderDTO dto = service.createProductionOrder(formulationId,ammount,persistence);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		if(persistence) {
			return ResponseEntity.created(uri).body(dto);
		}else {
			return ResponseEntity.ok().body(dto);
		}		
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> update(@PathVariable Long id,@RequestBody ProductionOrderDTO dto){
		dto = service.update(id,dto);
		return ResponseEntity.ok().body(dto); 
	}
	
}

