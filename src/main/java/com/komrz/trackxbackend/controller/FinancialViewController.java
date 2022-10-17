package com.komrz.trackxbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.CategoryFinancialViewDTO;
import com.komrz.trackxbackend.dto.CostCenterFinancialViewDTO;
import com.komrz.trackxbackend.dto.FinancialTrendFinancialViewFetchDTO;
import com.komrz.trackxbackend.dto.InfoFinancialViewDTO;
import com.komrz.trackxbackend.dto.ProgramFinancialViewDTO;
import com.komrz.trackxbackend.dto.TopContractFinancialViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.FinancialViewService;
import com.komrz.trackxbackend.service.LegalEntityService;
import com.komrz.trackxbackend.service.PortfolioService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@RestController
@RequestMapping("/financialview")
public class FinancialViewController {

	@Autowired
	private FinancialViewService financialViewService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	@Autowired
	private LegalEntityService legalEntityService;
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Info For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Info for Financial View Returned!", response = InfoFinancialViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InfoFinancialViewDTO> infoFinancialView(@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(financialViewService.infoFinancialView(tenantId, year));
	}
	
	/**
	 * 
	 * @param buySell
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@ApiOperation(value = "Get Top 10 Contracts For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Top 10 Contracts Returned!", response = TopContractFinancialViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/top10", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TopContractFinancialViewDTO>> topContractFinancialView(
								@RequestParam("BuySellEn") BuySellEn buySell,
								@RequestParam("StartDate") String startDate,
								@RequestParam("EndDate") String endDate){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(financialViewService.topContractFinancialView(tenantId, buySell, startDate, endDate));
	}
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Financial Trend For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Top 10 Contracts Returned!", response = TopContractFinancialViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/financialTrend", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FinancialTrendFinancialViewFetchDTO>> financialTrendFinancialView(@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(financialViewService.financialTrendFinancialView(tenantId, year));
	}
	
	/**
	 * 
	 * @param buySell
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Category For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Category For Financial View Returned!", response = CategoryFinancialViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CategoryFinancialViewDTO>> categoryFinancialView(@RequestParam("BuySellEn") BuySellEn buySell,
																				@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(financialViewService.categoryFinancialView(tenantId, buySell, year));
	}
	
	/**
	 * 
	 * @param portfolioId
	 * @param buySell
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Progarm For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Program For Financial View Returned!", response = ProgramFinancialViewDTO.class),
			@ApiResponse(code = 404, message = "Portfolio does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/program", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> programFinancialView(@RequestParam("PortfolioId") String portfolioId,
																				@RequestParam("BuySellEn") BuySellEn buySell,
																				@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(! portfolioService.isExist(portfolioId, tenantId)) {
			throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(financialViewService.programFinancialView(tenantId, portfolioId, buySell, year));
	}
	
	/**
	 * 
	 * @param legalEntityId
	 * @param buySell
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Cost Center For Financial View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cost Center For Financial View Returned!", response = CostCenterFinancialViewDTO.class),
			@ApiResponse(code = 404, message = "Legal Entity does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/costcenter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> costCenterFinancialView(@RequestParam("LegalEntityId") String legalEntityId,
														@RequestParam("BuySellEn") BuySellEn buySell,
														@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(! legalEntityService.isExist(legalEntityId, tenantId)) {
			throw new NotFoundException("Legal Entity does not exists!", "Legal Entity does not exists!");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(financialViewService.costCenterFinancialView(tenantId, legalEntityId, buySell, year));
	}
}
