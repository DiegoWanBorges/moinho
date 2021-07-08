package com.twokeys.moinho.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.StockBalanceDTO;
import com.twokeys.moinho.dto.StockBalanceTotalDTO;
import com.twokeys.moinho.dto.StockMovementDTO;
import com.twokeys.moinho.services.StockMovementService;

@RestController
@RequestMapping(value="/stocks")
public class StockMovementResource {
	@Autowired
	StockMovementService service;
	
	@GetMapping
	public ResponseEntity<Page<StockMovementDTO>> findByPagination(@RequestParam(value = "productId", defaultValue = "0") Long  productId,
																   @RequestParam(value = "startDate") LocalDate  startDate,
															       @RequestParam(value = "endDate") LocalDate  endDate,
														    	   @RequestParam(value = "page", defaultValue = "0") Integer page,
																   @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
																   @RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
																   @RequestParam(value = "direction", defaultValue = "DESC") String direction){
			PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
			Page<StockMovementDTO> list = service.findByStartDateAndProduct(productId,startDate,endDate, pageRequest);
			return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<List<StockMovementDTO>> findByProduct(@RequestParam(value = "productId") Long  productId){
		List<StockMovementDTO> list = service.findByProduct(productId);
		return ResponseEntity.ok().body(list);
	}
	
	@RequestMapping(value = "/currentStock", method = RequestMethod.GET)
	public ResponseEntity<StockBalanceDTO> currentStockByProduct(@RequestParam(value = "productId") Long  productId){
		StockBalanceDTO dto = service.currentStockByProduct(productId);
		return ResponseEntity.ok().body(dto);
	}
	@RequestMapping(value = "/stockmovement", method = RequestMethod.GET)
	public ResponseEntity<StockBalanceTotalDTO> stockByDateBetweenAndProductId(@RequestParam(value = "productId") Long  productId,
																			   @RequestParam(value = "startDate") LocalDate  startDate,
																			   @RequestParam(value = "endDate") LocalDate  endDate){
		StockBalanceTotalDTO dto = service.stockByDateBetweenAndProductId(startDate,endDate,productId);
		return ResponseEntity.ok().body(dto);
	}
	
	@RequestMapping(value = "/stockpreviousdate", method = RequestMethod.GET)
	public ResponseEntity<StockBalanceDTO> stockByPreviousDateAndProductId(@RequestParam(value = "productId") Long  productId,
																		   @RequestParam(value = "date") LocalDate  date){
		StockBalanceDTO dto = service.stockByPreviousDateAndProductId(productId,date);
		return ResponseEntity.ok().body(dto);
	}
	
	@RequestMapping(value = "/stockpreviousandequaldate", method = RequestMethod.GET)
	public ResponseEntity<StockBalanceDTO> stockByPreviousAndEqualDateAndProductId(@RequestParam(value = "productId") Long  productId,
																	               @RequestParam(value = "date") LocalDate  date){
		StockBalanceDTO dto = service.stockByProductAndPreviousAndEqualDate(productId,date);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<StockMovementDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<StockMovementDTO> insert(@Valid @RequestBody StockMovementDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<StockMovementDTO> update(@PathVariable Long id,@RequestBody StockMovementDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<StockMovementDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

