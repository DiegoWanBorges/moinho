package com.twokeys.moinho.resources;

import java.net.URI;
import java.util.List;

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

import com.twokeys.moinho.dto.LineDTO;
import com.twokeys.moinho.services.LineService;

@RestController
@RequestMapping(value="/lines")
public class LineResource {
	@Autowired
	LineService service;
	
	@GetMapping
	public ResponseEntity<List<LineDTO>> findAll(String name){
		List<LineDTO> list = service.findByNameLikeIgnoreCase(name);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<LineDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<LineDTO> insert(@RequestBody LineDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<LineDTO> update(@PathVariable Long id,@RequestBody LineDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<LineDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}
