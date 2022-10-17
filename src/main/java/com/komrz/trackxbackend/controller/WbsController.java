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

import com.komrz.trackxbackend.dto.WbsCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.CostCenterPOJO;
import com.komrz.trackxbackend.model.WbsPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CostCenterService;
import com.komrz.trackxbackend.service.WbsService;

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
public class WbsController {

	@Autowired
	WbsService wbsService;
	
	@Autowired
	CostCenterService costCenterService;
	
	/**
	 * 
	 * @param costCenterId
	 * @return
	 */
	@ApiOperation(value = "Get All Wbs By Cost Center Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Wbs Returned!", response = WbsPOJO.class),
			@ApiResponse(code = 404, message = "Cost Center does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/wbs")
	public ResponseEntity<?> getAllWbs(@RequestParam("CostCenterId") String costCenterId) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		CostCenterPOJO costCenter = costCenterService.fetchCostCenterId(costCenterId);
		if(costCenter == null) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if(tenantId.equals(costCenter.getLegalEntity().getTenantId())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(wbsService.getAllWbs(costCenterId));
		}
		throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
	}
	
	/**
	 * 
	 * @param wbsId
	 * @return
	 */
	@ApiOperation(value = "Get Wbs Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Wbs Returned!", response = WbsPOJO.class),
			@ApiResponse(code = 404, message = "Wbs does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/wbs/{id}")
	public ResponseEntity<?> fetchWbsById(@PathVariable(value = "id") String wbsId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		WbsPOJO wbs = wbsService.fetchWbsId(wbsId);
		if (wbs == null) {
			throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
		}
		if(tenantId.equals(wbs.getCostCenter().getLegalEntity().getTenantId())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(wbsService.fetchWbsId(wbsId));
		}
		throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
	}
	
	@ApiOperation(value = "Add New Wbs", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Cost Center does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Wbs Name!")
	})
	@PostMapping( value = "/wbs/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createWbs(@RequestBody @Valid WbsCreateDTO wbsCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		CostCenterPOJO costCenter = costCenterService.fetchCostCenterId(wbsCreateDTO.getCostCenterId());
		if(costCenter == null) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if(tenantId.equals(costCenter.getLegalEntity().getTenantId())) {
			if(wbsService.isExist(wbsCreateDTO, tenantId)) {
				throw new ConflictException("Duplicate Wbs Name!", "Duplicate Wbs Name!");
			}
			wbsService.createWbs(wbsCreateDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
	}
	
	@ApiOperation(value = "Update Wbs", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Wbs does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Wbs Name!")
	})
	@PutMapping( value = "/wbs/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateWbs(@PathVariable(value = "id") String wbsId,
			@RequestBody @Valid WbsCreateDTO wbsCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		WbsPOJO wbs = wbsService.fetchWbsId(wbsId);
		if (wbs == null) {
			throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
		}
		if(tenantId.equals(wbs.getCostCenter().getLegalEntity().getTenantId())) {
			if(wbsService.isExist(wbsCreateDTO, tenantId)) {
				throw new ConflictException("Duplicate Wbs Name!", "Duplicate Wbs Name!");
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(wbsService.updateWbs(wbsId, wbsCreateDTO));
		}
		throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
	}	
}
