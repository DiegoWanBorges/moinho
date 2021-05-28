package com.twokeys.moinho.resources;

import java.net.URI;
import java.time.LocalDate;

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

import com.twokeys.moinho.dto.LaborPaymentDTO;
import com.twokeys.moinho.services.LaborPaymentService;

@RestController
@RequestMapping(value="/laborpayments")
public class LaborPaymentResource {
	@Autowired
	LaborPaymentService service;
	
	@GetMapping
	public ResponseEntity<Page<LaborPaymentDTO>> findByDateAndEmployeeAndLaborCostType(@RequestParam(value = "employeeId", defaultValue = "0") Long  employeeId,
																					   @RequestParam(value = "laborCostTypeId", defaultValue = "0") Long  laborCostTypeId,
																					   @RequestParam(value = "startDate") LocalDate  startDate,
																					   @RequestParam(value = "endDate") LocalDate  endDate,
																					   @RequestParam(value = "page", defaultValue = "0") Integer page,
																					   @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
																					   @RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
																					   @RequestParam(value = "direction", defaultValue = "DESC") String direction){
			PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
			Page<LaborPaymentDTO> list = service.findByDateAndEmployeeAndLaborCostType(employeeId,laborCostTypeId,startDate,endDate, pageRequest);
			return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<LaborPaymentDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<LaborPaymentDTO> insert(@RequestBody LaborPaymentDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<LaborPaymentDTO> update(@PathVariable Long id,@RequestBody LaborPaymentDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<LaborPaymentDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

