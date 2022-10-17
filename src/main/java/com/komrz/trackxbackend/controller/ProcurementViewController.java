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

import com.komrz.trackxbackend.dto.ActiveContractEventProcurementView;
import com.komrz.trackxbackend.dto.ContractExpiryProcurementViewDTO;
import com.komrz.trackxbackend.dto.TopVendorProcurementViewDTO;
import com.komrz.trackxbackend.dto.VendorStatusProcurementViewDTO;
import com.komrz.trackxbackend.dto.VendorWiseCategoryProcurementViewDTO;
import com.komrz.trackxbackend.dto.YoyComparisonProcurementViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ProcurementViewService;

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
@RequestMapping("/procurementview")
public class ProcurementViewController {

	@Autowired
	private ProcurementViewService procurementViewService;
	
	/**
	 * 
	 * @param buySell
	 * @return
	 */
	@ApiOperation(value = "VendorStatus For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "VendorStatus For Procurement View Returned!", response = VendorStatusProcurementViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/vendorstatus", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> vendorStatusProcurementView(@RequestParam("BuySellEn") BuySellEn buySell){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.vendorStatusProcurementView(tenantId, buySell));
	}
	
	/**
	 * 
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "YoyComparison For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "YoyComparison For Procurement View Returned!", response = YoyComparisonProcurementViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/yoycomp", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<YoyComparisonProcurementViewDTO>> yoyComparisonProcurementView(@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.yoyComparisonProcurementView(tenantId, year));
	}
	
	/**
	 * 
	 * @param buySell
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Top Vendor For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Top Vendor For Procurement View Returned!", response = TopVendorProcurementViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/topvendor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TopVendorProcurementViewDTO>> topVendorProcurementView(@RequestParam("BuySellEn") BuySellEn buySell,
																					@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.topVendorProcurementView(tenantId, buySell, year));
	}
	
	/**
	 * 
	 * @param period
	 * @return
	 */
	@ApiOperation(value = "Contract Expiry For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Expiry For Procurement View Returned!", response = ContractExpiryProcurementViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/conexpiry", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContractExpiryProcurementViewDTO>> contractExpiryProcurementView(@RequestParam("startPeriod") Integer startPeriod,
																	@RequestParam("endPeriod") Integer endPeriod){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.contractExpiryProcurementView(tenantId, startPeriod, endPeriod));
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Active Contract With No Event For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Active Contract With No Event For Procurement View Returned!", response = ActiveContractEventProcurementView.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/activeconEvent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ActiveContractEventProcurementView>> activeContractProcurementView(){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.activeContractEventProcurementView(tenantId));
	}
	
	/**
	 * 
	 * @param buySell
	 * @param year
	 * @return
	 */
	@ApiOperation(value = "Vendor Wise Spend And Revenue For Procurement View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Vendor Wise Spend And Revenue For Procurement View Returned!", response = VendorWiseCategoryProcurementViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/vendorwisecategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> vendorWiseCategoryProcurementView(@RequestParam("BuySellEn") BuySellEn buySell,
																					@RequestParam("Year") String year){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(procurementViewService.vendorWiseCategoryProcurementView(tenantId, buySell, year));
	}
}
