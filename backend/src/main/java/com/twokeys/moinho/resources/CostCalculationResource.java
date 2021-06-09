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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.CostCalculationDTO;
import com.twokeys.moinho.services.CostCalculationService;

@RestController
@RequestMapping(value="/costcalculations")
public class CostCalculationResource {
	@Autowired
	CostCalculationService service;

	@GetMapping(value="/{id}")
	public ResponseEntity<CostCalculationDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping(value="/{id}")
	public ResponseEntity<String> calculation(@PathVariable Long id){
		service.calculation(id);
		return  ResponseEntity.ok().body("Ok");
	}
	@PostMapping
	public ResponseEntity<CostCalculationDTO> insert(@RequestBody CostCalculationDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<CostCalculationDTO> update(@PathVariable Long id,@RequestBody CostCalculationDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<CostCalculationDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

