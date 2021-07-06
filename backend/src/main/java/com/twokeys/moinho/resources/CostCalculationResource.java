package com.twokeys.moinho.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.twokeys.moinho.dto.CostCalculationDTO;
import com.twokeys.moinho.dto.CostCalculationResultDTO;
import com.twokeys.moinho.services.CostCalculationService;
import com.twokeys.moinho.services.exceptions.UntreatedException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@RequestMapping(value="/costcalculations")
public class CostCalculationResource {
	@Autowired
	CostCalculationService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<CostCalculationResultDTO> findById(@PathVariable Long id){
		return  ResponseEntity.ok().body(service.findResultById(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<CostCalculationDTO>> findByStartDateAndFormulation(@RequestParam(value = "startDate") LocalDate  startDate,
																				  @RequestParam(value = "endDate") LocalDate  endDate,
																				  @RequestParam(value = "page", defaultValue = "0") Integer page,
																				  @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
																				  @RequestParam(value = "orderBy", defaultValue = "referenceMonth") String orderBy,
																				  @RequestParam(value = "direction", defaultValue = "DESC") String direction){
			PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction),orderBy);
			Page<CostCalculationDTO> list = service.findByReferenceMonthAndStatus(startDate,endDate, pageRequest);
			return ResponseEntity.ok().body(list);
	}
		
	@PostMapping
	public ResponseEntity<CostCalculationDTO> insert(@RequestBody CostCalculationDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				  .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<CostCalculationDTO> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build(); 
	}
	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> pdf(@RequestParam(value = "id") Long id) throws FileNotFoundException, JRException {
		try {
			/*Cost Calculation*/
			CostCalculationResultDTO dto = service.findResultById(id);
			List<CostCalculationResultDTO> list = new ArrayList<>();
			list.add(dto);
			JRBeanCollectionDataSource dtSourceCostCalculation = new JRBeanCollectionDataSource(list);
			JasperReport costCalculationReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/costCalculation.jrxml"));
			HashMap<String, Object> mapCosCalculation = new HashMap<>();
			JasperPrint costCalculation = JasperFillManager.fillReport(costCalculationReport, mapCosCalculation, dtSourceCostCalculation);
			
			/*Initial Stock*/
			Instant initialStockDate = dto.getCostCalculation().getStockStartDate().atStartOfDay(ZoneId.of("America/Sao_Paulo")).toInstant();
			JRBeanCollectionDataSource dtSourceInitialStock = new JRBeanCollectionDataSource(dto.getOpeningStockBalance());
			JasperReport initialStockReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/initialStock.jrxml"));
			HashMap<String, Object> mapInitialStock = new HashMap<>();
			mapInitialStock.put("initialStockDate",initialStockDate);
			JasperPrint initialStock = JasperFillManager.fillReport(initialStockReport, mapInitialStock, dtSourceInitialStock);
			
			/*Purchase Stock*/
			JRBeanCollectionDataSource dtSourcePurchase = new JRBeanCollectionDataSource(dto.getPurchaseStockBalance());
			JasperReport purchaseReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/purchaseStock.jrxml"));
			HashMap<String, Object> mapPurchase = new HashMap<>();
			mapPurchase.put("initialDate",dto.getCostCalculation().getStartDate());
			mapPurchase.put("endDate",dto.getCostCalculation().getEndDate());
			JasperPrint purchaseStock = JasperFillManager.fillReport(purchaseReport, mapPurchase, dtSourcePurchase);
			
			/*Adjustment Stock*/
			JRBeanCollectionDataSource dtSourceAdjustment = new JRBeanCollectionDataSource(dto.getAdjustmentStockBalance());
			JasperReport adjustmentReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/adjustmentStock.jrxml"));
			HashMap<String, Object> mapAdjustment = new HashMap<>();
			mapAdjustment.put("initialDate",dto.getCostCalculation().getStartDate());
			mapAdjustment.put("endDate",dto.getCostCalculation().getEndDate());
			JasperPrint adjustmentStock = JasperFillManager.fillReport(adjustmentReport, mapAdjustment, dtSourceAdjustment);
			
			/*Production Order*/
			JRBeanCollectionDataSource dtSourceProductionOrder = new JRBeanCollectionDataSource(dto.getProductionOrders());
			JasperReport productionOrderReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/productionOrder.jrxml"));
			HashMap<String, Object> mapProductionOrder = new HashMap<>();
			mapProductionOrder.put("initialDate",dto.getCostCalculation().getStartDate());
			mapProductionOrder.put("endDate",dto.getCostCalculation().getEndDate());
			JasperPrint productionOrder = JasperFillManager.fillReport(productionOrderReport, mapProductionOrder, dtSourceProductionOrder);
			
			/*Production Average Cost*/
			JRBeanCollectionDataSource dtSourceProductionAverageCost = new JRBeanCollectionDataSource(dto.getProductionOrderProducedAverageCosts());
			JasperReport productionAverageCostReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/productionAverageCost.jrxml"));
			HashMap<String, Object> mapProductionAverageCost = new HashMap<>();
			mapProductionAverageCost.put("initialDate",dto.getCostCalculation().getStartDate());
			mapProductionAverageCost.put("endDate",dto.getCostCalculation().getEndDate());
			JasperPrint productionAverageCost = JasperFillManager.fillReport(productionAverageCostReport, mapProductionAverageCost, dtSourceProductionAverageCost);
			
			/*End Stock*/
			JRBeanCollectionDataSource dtSourceEndStock = new JRBeanCollectionDataSource(dto.getClosingStockBalance());
			JasperReport endStockReport = JasperCompileManager.compileReport(
					new FileInputStream("src/main/resources/reports/costCalculation/result/endStock.jrxml"));
			HashMap<String, Object> mapEndStock = new HashMap<>();
			mapEndStock.put("initialStockDate",dto.getCostCalculation().getEndDate());
			JasperPrint endStock = JasperFillManager.fillReport(endStockReport, mapEndStock, dtSourceEndStock);
			
			/*Added pages into main report*/
			for (int i = 0; i < initialStock.getPages().size(); i++) {
				JRPrintPage object = initialStock.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			for (int i = 0; i < purchaseStock.getPages().size(); i++) {
				JRPrintPage object = purchaseStock.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			for (int i = 0; i < adjustmentStock.getPages().size(); i++) {
				JRPrintPage object = adjustmentStock.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			for (int i = 0; i < productionOrder.getPages().size(); i++) {
				JRPrintPage object = productionOrder.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			for (int i = 0; i < productionAverageCost.getPages().size(); i++) {
				JRPrintPage object = productionAverageCost.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			for (int i = 0; i < endStock.getPages().size(); i++) {
				JRPrintPage object = endStock.getPages().get(i);
				costCalculation.addPage(object);
			}
			
			byte[] data = JasperExportManager.exportReportToPdf(costCalculation);
			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=formulation.pdf");
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		} catch (Exception e) {
			throw new UntreatedException(e.getMessage()); 
		}
	}
}

