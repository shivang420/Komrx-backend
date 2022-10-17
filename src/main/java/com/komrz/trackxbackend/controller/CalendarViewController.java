package com.komrz.trackxbackend.controller;

import java.util.List;

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

import com.komrz.trackxbackend.dto.LatestEventCalendarViewDTO;
import com.komrz.trackxbackend.dto.NewContractCalendarViewDTO;
import com.komrz.trackxbackend.dto.RenewalContractCalendarViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CalendarViewService;

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
@RequestMapping("/calendarview")
public class CalendarViewController {

	@Autowired
	private CalendarViewService calendarViewService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Latest 10 Events for Calendar View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Latest Events Returned!", response = LatestEventCalendarViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/latestevent", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LatestEventCalendarViewDTO>> latestEventCalendarView(){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(calendarViewService.latestEventCalendarView(tenantId));
	}
	
	/**
	 * 
	 * @param buySell
	 * @return
	 */
	@ApiOperation(value = "New Contract for Calendar View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "New Contracts Returned!", response = NewContractCalendarViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/newcontract/{BuySell}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NewContractCalendarViewDTO>> newContractCalendarView(@PathVariable("BuySell") BuySellEn buySell){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(calendarViewService.newContractCalendarView(tenantId, buySell));
	}
	
	/**
	 * 
	 * @param buySell
	 * @return
	 */
	@ApiOperation(value = "Renewal Contract for Calendar View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract To be Renewed Returned!", response = RenewalContractCalendarViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/renewalcontract/{BuySell}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RenewalContractCalendarViewDTO>> renewalContractCalendarView(@PathVariable("BuySell") BuySellEn buySell){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(calendarViewService.renewalContractCalendarView(tenantId, buySell));
	}
}
