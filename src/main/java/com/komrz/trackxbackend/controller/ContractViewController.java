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

import com.komrz.trackxbackend.dto.AllContractViewDTO;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ContractViewService;

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
@RequestMapping("/contractview")
public class ContractViewController {

	@Autowired
	private ContractViewService contractViewService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "All Contracts For Contract View", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "All Contracts Returned!", response = AllContractViewDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/allcontracts", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AllContractViewDTO>> allContractView(@RequestParam(value = "contractStatus") ContractStatusEn contractStatus){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(contractViewService.allContractView(tenantId, contractStatus));
	}
}
