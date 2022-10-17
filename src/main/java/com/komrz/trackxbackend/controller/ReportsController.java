package com.komrz.trackxbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.ContractSummaryDTO;
import com.komrz.trackxbackend.dto.PortfolioSummaryDTO;
import com.komrz.trackxbackend.dto.VendorSummaryDTO;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ContractService;
import com.komrz.trackxbackend.service.PortfolioService;
import com.komrz.trackxbackend.service.ReportsService;
import com.komrz.trackxbackend.service.VendorService;

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
@RequestMapping()
public class ReportsController {

	@Autowired
	private ReportsService reportsService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private PortfolioService portfolioService;
	
	/**
	 * 
	 * @param contractId
	 * @return
	 */
	@ApiOperation(value = "Contract Summary", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Summary Returned!", response = ContractSummaryDTO.class),
			@ApiResponse(code = 404, message = "Contract does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contractsummary/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> contractSummary(@PathVariable(value = "contractId")String contractId){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if (contractService.isExist(contractId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportsService.contractSummary(contractId));
		}
		throw new NotFoundException("Contract does not exists!", "Contract does not exists!");
	}
	
	@ApiOperation(value = "Vendor Summary", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Vendor Summary Returned!", response = VendorSummaryDTO.class),
			@ApiResponse(code = 404, message = "Vendor does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/vendorsummary/{contractId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> vendorSummary(@PathVariable(value = "vendorId")String vendorId){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if (vendorService.isExist(vendorId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportsService.vendorSummary(vendorId));
		}
		throw new NotFoundException("Vendor does not exists!", "Vendor does not exists!");
	}
	
	@ApiOperation(value = "Portfolio Summary", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Portfolio Summary Returned!", response = PortfolioSummaryDTO.class),
			@ApiResponse(code = 404, message = "Portfolio does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/portfoliosummary/{portfolioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> portfolioSummary(@PathVariable(value = "portfolioId")String portfolioId){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if (portfolioService.isExist(portfolioId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reportsService.portfolioSummary(portfolioId));
		}
		throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
	}
}
