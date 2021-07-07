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

import com.twokeys.moinho.dto.OperationalPaymentDTO;
import com.twokeys.moinho.services.OperationalPaymentService;

@RestController
@RequestMapping(value = "/operationalpayments")
public class OperationalPaymentResource {
	@Autowired
	OperationalPaymentService service;

	@GetMapping
	public ResponseEntity<Page<OperationalPaymentDTO>> findByPagination(@RequestParam(value = "operationalCostId", defaultValue = "0") Long operationalCostId,
																		@RequestParam(value = "startDate") LocalDate startDate, @RequestParam(value = "endDate") LocalDate endDate,
																		@RequestParam(value = "page", defaultValue = "0") Integer page,
																		@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
																		@RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
																		@RequestParam(value = "direction", defaultValue = "DESC") String direction) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<OperationalPaymentDTO> list = service.findByDateAndType(operationalCostId, startDate, endDate,
				pageRequest);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OperationalPaymentDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<OperationalPaymentDTO> insert(@RequestBody OperationalPaymentDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<OperationalPaymentDTO> update(@PathVariable Long id, @RequestBody OperationalPaymentDTO dto) {
		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<OperationalPaymentDTO> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
