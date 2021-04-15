package com.twokeys.moinho.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@GetMapping
	public ResponseEntity<ProductionOrderDTO> findById(@RequestParam(value = "formulationId") Long  formulationId,
													   @RequestParam(value = "ammount") Double ammount){
		return  ResponseEntity.ok().body(service.createProductionOrder(formulationId,ammount));
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<ProductionOrderDTO> insert(@RequestBody ProductionOrderDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> update(@PathVariable Long id,@RequestBody ProductionOrderDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

