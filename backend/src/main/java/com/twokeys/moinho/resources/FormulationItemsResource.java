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

import com.twokeys.moinho.dto.FormulationItemDTO;
import com.twokeys.moinho.services.FormulationItemService;

@RestController
@RequestMapping(value="/formulationsitems")
public class FormulationItemsResource {
	@Autowired
	FormulationItemService service;
		
	@GetMapping(value="/{idFormulation},{idProduct}")
	public ResponseEntity<FormulationItemDTO> findById(@PathVariable Long  idFormulation,@PathVariable Long  idProduct){
		return  ResponseEntity.ok().body(service.findById(idFormulation,idProduct));
	}
	
	@PostMapping
	public ResponseEntity<FormulationItemDTO> insert(@RequestBody FormulationItemDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getFormulationId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping
	public ResponseEntity<FormulationItemDTO> update(@RequestBody FormulationItemDTO dto){
		dto = service.update(dto);
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{idFormulation},{idProduct}")
	public ResponseEntity<FormulationItemDTO> delete(@PathVariable Long  idFormulation,
													  @PathVariable Long  idProduct){
		service.delete(idFormulation,idProduct);
		return ResponseEntity.noContent().build(); 
	}
}

