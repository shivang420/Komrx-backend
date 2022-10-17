package com.komrz.trackxbackend.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.JwtAuthenticationResponse;
import com.komrz.trackxbackend.dto.TenantDetailsCreateDTO;
import com.komrz.trackxbackend.enumerator.IndustryTypeEn;
import com.komrz.trackxbackend.exception.BadRequestException;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.security.JwtTokenProvider;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CustomUserDetailsService;
import com.komrz.trackxbackend.service.TenantDetailsService;

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
public class TenantDetailsController {

	@Autowired
	private TenantDetailsService tenantDetailsService;
	
	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	/**
	 * 
	 * @param tenantDetailsCreateDTO
	 * @param bindingResult
	 * @return
	 */
	@ApiOperation(value = "Add New Tenant", code = 200)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "JwtAuthenticationResponse Returned!", response = JwtAuthenticationResponse.class),
			@ApiResponse(code = 400, message = "Username Already exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping( value = "/tenant/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTenant( @RequestBody @Valid TenantDetailsCreateDTO tenantDetailsCreateDTO) {
		try {
			try {
				userService.loadUserByUsername(tenantDetailsCreateDTO.getUsername());
				throw new ConflictException("Username Already Exists", "Username Already Exists");
			} catch (UsernameNotFoundException e) {
				String tenantId = tenantDetailsService.createTenant(tenantDetailsCreateDTO);
				userService.createUser(tenantDetailsCreateDTO, "ROL001", tenantId);
				Authentication authentication = authenticationManager.authenticate(
		                new UsernamePasswordAuthenticationToken(
		                		tenantDetailsCreateDTO.getUsername().toLowerCase(),
		                		tenantDetailsCreateDTO.getPassword()
		                )
		        );
		        SecurityContextHolder.getContext().setAuthentication(authentication);
		        String jwt = tokenProvider.generateToken(authentication);
		        return ResponseEntity.status(HttpStatus.OK)
		        		.body(new JwtAuthenticationResponse(jwt));
			}
		} catch (Exception e) {
			throw new BadRequestException("Bad Request", "Bad Request");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Tenant Pref Curr", code = 200, authorizations = {@Authorization("JWT")})
	@GetMapping( value = "/tenant/curr")
	public ResponseEntity<String> fetchTenantPrefCurrByID() {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(tenantDetailsService.getPrefCurrency(tenantId));
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Industry Type", code = 200)
	@GetMapping(value = "/industrytype")
	public ResponseEntity<?> getIndustryTypes(){
		return ResponseEntity.ok(IndustryTypeEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Pref Currencies", code = 200)
	@GetMapping(value = "/prefcurr")
	public ResponseEntity<?> getPrefCurrency(){
		List<String> prefCurr = new ArrayList<String>();
		prefCurr.add("USD");
		prefCurr.add("INR");
		prefCurr.add("EUR");
		return ResponseEntity.ok(prefCurr);
	}
}
