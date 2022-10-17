package com.komrz.trackxbackend.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.CostCenterCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.CostCenterPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CostCenterService;
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
public class CostCenterController {

	@Autowired
	CostCenterService costCenterService;
	
	@Autowired
	LegalEntityService legalEntityService;
	
	/**
	 * 
	 * @param legalEntityId
	 * @return
	 */
	@ApiOperation(value = "Get All Cost Centers By Legal Entity Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Cost Center Returned!", response = CostCenterPOJO.class),
			@ApiResponse(code = 404, message = "Legal Entity does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/costCenter")
	public ResponseEntity<?> getAllCostCenters(@RequestParam(value = "LegalEntityId") String legalEntityId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(legalEntityService.isExist(legalEntityId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(costCenterService.getAllCostCenter(legalEntityId));
		}
		throw new NotFoundException("Legal Entity does not exists!", "Legal Entity does not exists!");
	}
	
	/**
	 * 
	 * @param costCenterId
	 * @return
	 */
	@ApiOperation(value = "Get Cost Center Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cost Center Returned!", response = CostCenterPOJO.class),
			@ApiResponse(code = 404, message = "Cost Center does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/costCenter/{id}")
	public ResponseEntity<?> fetchCostCenterById(@PathVariable(value = "id") String costCenterId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		CostCenterPOJO costCenter = costCenterService.fetchCostCenterId(costCenterId);
		if(costCenter == null) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if(tenantId.equals(costCenter.getLegalEntity().getTenantId())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(costCenterService.fetchCostCenterId(costCenterId));
		}
		throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
	}
	
	/**
	 * 
	 * @param costCenterCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Cost Center", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Legal Entity does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Cost Center Name!")
	})
	@PostMapping( value = "/costCenter/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createCostCenter(@RequestBody @Valid CostCenterCreateDTO costCenterCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExistLegalEntity = legalEntityService.isExist(costCenterCreateDTO.getLegalEntityId(), tenantId);
		if(isExistLegalEntity == false) {
			throw new NotFoundException("Legal Entity does not exists!", "Legal Entity does not exists!");
		}
		
		boolean isExist = costCenterService.isExist(costCenterCreateDTO, tenantId);
		if(isExist == false) {
			costCenterService.createCostCenter(costCenterCreateDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Cost Center Name!", "Duplicate Cost Center Name!");
	}
	
	/**
	 * 
	 * @param costCenterId
	 * @param costCenterCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Cost Center", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Cost Center does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Cost Center Name!")
	})
	@PutMapping( value = "/costCenter/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCostCenter(@PathVariable(value = "id") String costCenterId,
			@RequestBody @Valid CostCenterCreateDTO costCenterCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		CostCenterPOJO costCenter = costCenterService.fetchCostCenterId(costCenterId);
		if(costCenter == null) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if(tenantId.equals(costCenter.getLegalEntity().getTenantId())) {
			boolean isExistNew = costCenterService.isExist(costCenterCreateDTO, tenantId);
			if(isExistNew == false) {
				costCenterService.updateCostCenter(costCenterId, costCenterCreateDTO);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Cost Center Name!", "Duplicate Cost Center Name!");
			}
		}
		throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
	}	
}
