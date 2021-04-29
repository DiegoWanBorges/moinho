package com.twokeys.moinho.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twokeys.moinho.dto.ParameterDTO;
import com.twokeys.moinho.services.ParameterService;

@RestController
@RequestMapping(value="/parameters")
public class ParameterResource {
	@Autowired
	ParameterService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<ParameterDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<ParameterDTO> update(@PathVariable Long id,@RequestBody ParameterDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	
}

