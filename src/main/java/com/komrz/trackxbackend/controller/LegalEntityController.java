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

import com.komrz.trackxbackend.dto.LegalEntityCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.LegalEntityPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.LegalEntityService;

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
public class LegalEntityController {

	@Autowired
	private LegalEntityService legalEntityService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get All LegalEntitys", code = 200, authorizations = {@Authorization("JWT")})
	@GetMapping( value = "/legalEntity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LegalEntityPOJO>> getAllLegalEntitys() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
				.body(legalEntityService.fetchLegalEntityTenantId(tenantId));
	}
	
	/**
	 * 
	 * @param legalEntityId
	 * @return
	 */
	@ApiOperation(value = "Get LegalEntity Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Legal Entity Returned!", response = LegalEntityPOJO.class),
			@ApiResponse(code = 404, message = "Legal Entity does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/legalEntity/{id}")
	public ResponseEntity<?> fetchLegalEntityById(@PathVariable(value = "id") String legalEntityId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(legalEntityService.isExist(legalEntityId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(legalEntityService.fetchLegalEntityId(legalEntityId));
		}
		throw new NotFoundException("Legal Entity does not exists!", "Legal Entity does not exists!");
	}
	
	/**
	 * 
	 * @param legalEntityCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New LegalEntity", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 409, message = "Duplicate Legal Entity Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/legalEntity/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createLegalEntity(@RequestBody @Valid LegalEntityCreateDTO legalEntityCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = legalEntityService.isExist(legalEntityCreateDTO, tenantId);
		if(isExist == false) {
			legalEntityService.createLegalEntity(legalEntityCreateDTO, tenantId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Legal Entity Name!", "Duplicate Legal Entity Name!");
	}
	
	/**
	 * 
	 * @param legalEntityId
	 * @param legalEntityCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update LegalEntity",code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Legal Entity does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Legal Entity Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/legalEntity/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateLegalEntity(@PathVariable(value = "id") String legalEntityId,
			@RequestBody LegalEntityCreateDTO legalEntityCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = legalEntityService.isExist(legalEntityId, tenantId);
		if(isExist == true) {
			boolean isExistNew = legalEntityService.isExist(legalEntityCreateDTO, tenantId);
			if(isExistNew == false) {
				legalEntityService.updateLegalEntity(legalEntityId, legalEntityCreateDTO);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Legal Entity Name!", "Duplicate Legal Entity Name!");
			}
		}
		throw new NotFoundException("Legal Entity does not exists!", "Legal Entity does not exists!");
	}	
}
