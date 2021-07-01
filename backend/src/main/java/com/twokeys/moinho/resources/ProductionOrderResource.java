package com.twokeys.moinho.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import com.twokeys.moinho.dto.ProductionOrderItemDTO;
import com.twokeys.moinho.services.ProductionOrderService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping(value="/productionorders")
public class ProductionOrderResource {
	@Autowired
	ProductionOrderService service;
	
	@GetMapping
	public ResponseEntity<Page<ProductionOrderDTO>> findByStartDateAndFormulation(
			@RequestParam(value = "formulationId", defaultValue = "0") Long  formulationId,
			@RequestParam(value = "startDate") LocalDateTime  startDate,
			@RequestParam(value = "endDate") LocalDateTime  endDate,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "emission") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction
			){
			Instant start = startDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
			Instant end = endDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
			PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
			Page<ProductionOrderDTO> list = service.findByStartDateAndFormulation(formulationId,start,end, pageRequest);
			return ResponseEntity.ok().body(list);
	}
	
	
	@PostMapping
	public ResponseEntity<ProductionOrderDTO> createProductionOrder(@RequestParam(value = "formulationId") Long  formulationId,
													   				@RequestParam(value = "ammount") Double ammount,
													   				@RequestParam(value = "persistence", defaultValue = "false") Boolean persistence,
													   				@RequestParam(value = "startDate")LocalDateTime  startDate){
		Instant start = startDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant();
		ProductionOrderDTO dto = service.createProductionOrder(formulationId,ammount,persistence,start);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		if(persistence) {
			return ResponseEntity.created(uri).body(dto);
		}else {
			return ResponseEntity.ok().body(dto);
		}		
	}
	
	@RequestMapping(params = "pdf")
	public ResponseEntity<byte[]> pdf(@RequestParam(value ="pdf") Long id) throws FileNotFoundException, JRException{
		ProductionOrderDTO dto = service.findById(id);
		
		List<ProductionOrderItemDTO> list = new ArrayList<>();
		list.addAll(dto.getProductionOrderItems());
		
		JRBeanCollectionDataSource beanCollectionDataSource = new  JRBeanCollectionDataSource(list);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports/productionOrder/productionOrder.jrxml"));
		
		HashMap<String,Object> map = new HashMap<>(); 
		map.put("productionOrder", dto );
		JasperPrint report =  JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		byte[] data = JasperExportManager.exportReportToPdf(report);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=formulation.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
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
	@DeleteMapping(value="/{id}")
	public ResponseEntity<ProductionOrderDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

