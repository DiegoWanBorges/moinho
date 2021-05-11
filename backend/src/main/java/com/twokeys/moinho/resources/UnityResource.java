package com.twokeys.moinho.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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

import com.twokeys.moinho.dto.UnityDTO;
import com.twokeys.moinho.services.UnityService;

@RestController
@RequestMapping(value="/units")
public class UnityResource {
	@Autowired
	UnityService service;
	
	@GetMapping
	public ResponseEntity<Page<UnityDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "description", defaultValue = "") String  description,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "description") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<UnityDTO> list = service.findAllPaged(description,pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping
	@RequestMapping(params = "unitylist")
	public ResponseEntity<List<UnityDTO>> findAll(@RequestParam(value="unitylist") String description){
		List<UnityDTO> list = service.findByDescriptionLikeIgnoreCase(description);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<UnityDTO> findById(@PathVariable String id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<UnityDTO> insert(@RequestBody UnityDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<UnityDTO> update(@PathVariable String id,@RequestBody UnityDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<UnityDTO> delete(@PathVariable String id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

