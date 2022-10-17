package com.komrz.trackxbackend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.PortfolioCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.PortfolioPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
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
@RequestMapping()
public class PortfolioController {
	
	@Autowired
	private PortfolioService portfolioService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get All Portfolios", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List Of Portfolio Returned!", response = PortfolioPOJO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/portfolio", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PortfolioPOJO>> getAllPortfolioByTenant() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
				.body(portfolioService.fetchPortfolioTenantId(tenantId));
	}
	
	/**
	 * 
	 * @param portfolioId
	 * @return
	 */
	@ApiOperation(value = "Get Portfolio Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Portfolio Returned!", response = PortfolioPOJO.class),
			@ApiResponse(code = 404, message = "Portfolio does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/portfolio/{id}")
	public ResponseEntity<?> fetchPortfolioById(@PathVariable(value = "id") String portfolioId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(portfolioService.isExist(portfolioId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(portfolioService.fetchPortfolioId(portfolioId));
		}
		throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
	}
	
	/**
	 * 
	 * @param portfolioCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Portfolio", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 409, message = "Duplicate Portfolio Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/portfolio/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createPortfolio(@RequestBody @Valid PortfolioCreateDTO portfolioCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = portfolioService.isExist(portfolioCreateDTO, tenantId);
		if(isExist == false) {
			portfolioService.createPortfolio(portfolioCreateDTO, tenantId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Portfolio Name!", "Duplicate Portfolio Name!");
	}
	
	/**
	 * 
	 * @param portfolioId
	 * @param portfolioCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Portfolio", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Portfolio does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Portfolio Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/portfolio/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePortfolio(@PathVariable(value = "id") String portfolioId,
			@RequestBody PortfolioCreateDTO portfolioCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = portfolioService.isExist(portfolioId, tenantId);
		if(isExist == true) {
			boolean isExistNew = portfolioService.isExist(portfolioCreateDTO, tenantId);
			if(isExistNew == false) {
				portfolioService.updatePortfolio(portfolioId, portfolioCreateDTO);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Portfolio Name!", "Duplicate Portfolio Name!");
			}
		}
		throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
	}	
}
