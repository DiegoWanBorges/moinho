package com.twokeys.moinho.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
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

import com.twokeys.moinho.dto.FormulationDTO;
import com.twokeys.moinho.dto.FormulationItemDTO;
import com.twokeys.moinho.services.FormulationService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping(value="/formulations")
public class FormulationResource {
	@Autowired
	FormulationService service;
	
	@GetMapping
	public ResponseEntity<Page<FormulationDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "description", defaultValue = "") String  description,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "description") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction
			){
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
		Page<FormulationDTO> list = service.findAllPaged(description,pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping
	@RequestMapping(params = "listdescription")
	public ResponseEntity<List<FormulationDTO>> findAll(@RequestParam(value="listdescription") String description){
		List<FormulationDTO> list = service.findByNameLikeIgnoreCase(description);
		return ResponseEntity.ok().body(list);
	}
	@GetMapping
	@RequestMapping(params = "pdf")
	public ResponseEntity<byte[]> pdf(@RequestParam(value="pdf") Long id) throws FileNotFoundException, JRException{
		FormulationDTO dto = service.findById(id);
		
		List<FormulationItemDTO> list = new ArrayList<>();
		list.addAll(dto.getFormulationItems());
			
		
		
		JRBeanCollectionDataSource beanCollectionDataSource = new  JRBeanCollectionDataSource(list);
		
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports/formulation/formulation.jrxml"));
		
		
		
		HashMap<String,Object> map = new HashMap<>(); 
		map.put("description", dto.getDescription() +" - "+ dto.getCoefficient()+ " " + dto.getProduct().getUnity().getId() );
		map.put("sectors", dto.getSectors());
		map.put("operationalCosts", dto.getOperationalCostType());
		JasperPrint report =  JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		
		byte[] data = JasperExportManager.exportReportToPdf(report);
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=formulation.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
	@GetMapping(value="/{id}")
	public ResponseEntity<FormulationDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<FormulationDTO> insert(@RequestBody FormulationDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<FormulationDTO> update(@PathVariable Long id,@RequestBody FormulationDTO dto){
		dto = service.update(id,dto);
		
		return ResponseEntity.ok().body(dto); 
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<FormulationDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
}

