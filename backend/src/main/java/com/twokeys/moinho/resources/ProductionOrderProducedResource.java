package com.twokeys.moinho.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.ProductionOrderProducedDTO;
import com.twokeys.moinho.services.ProductionOrderProducedService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping(value="productionordersproduced")
public class ProductionOrderProducedResource {
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	ProductionOrderProducedService service;
	
	@RequestMapping(value  = "/reports",method = RequestMethod.GET )
	public ResponseEntity<byte[]> pdf(@RequestParam(value="id") Long productionOrderId, 
									  @RequestParam(value="pallet") Integer pallet) throws FileNotFoundException, JRException{
		ProductionOrderProducedDTO dto = service.findById(productionOrderId, pallet);
		
		List<ProductionOrderProducedDTO> list = new ArrayList<>();
		list.add(dto);
		
		
		Instant validity = dto.getManufacturingDate().plus(dto.getProduct().getValidityDays(),ChronoUnit.DAYS);
		
		
		logger.info(validity);
		JRBeanCollectionDataSource beanCollectionDataSource = new  JRBeanCollectionDataSource(list);
		JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports/productionOrderProduced/productionOrderTag.jrxml"));
		
		HashMap<String,Object> map = new HashMap<>(); 
		map.put("validity", validity);
		JasperPrint report =  JasperFillManager.fillReport(compileReport, map,beanCollectionDataSource);
		byte[] data = JasperExportManager.exportReportToPdf(report);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=formulation.pdf");
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
	}
	
	@PostMapping
	public ResponseEntity<ProductionOrderProducedDTO> insert(@RequestBody ProductionOrderProducedDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getProductionOrderId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	@PutMapping
	public ResponseEntity<ProductionOrderProducedDTO> update(@RequestBody ProductionOrderProducedDTO dto){
		dto = service.update(dto);
		return ResponseEntity.ok().body(dto);
	}
	@DeleteMapping
	public ResponseEntity<ProductionOrderProducedDTO> delete(@RequestParam(value = "productionOrderId") Long productionOrderId,
														 	 @RequestParam(value = "pallet") Integer pallet){
		service.delete(productionOrderId, pallet);
		return ResponseEntity.noContent().build(); 
	}
	
}

