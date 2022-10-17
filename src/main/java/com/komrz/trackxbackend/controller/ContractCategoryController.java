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

import com.komrz.trackxbackend.dto.ContractCategoryCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.ContractCategoryPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ContractCategoryService;

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
public class ContractCategoryController {

	@Autowired
	private ContractCategoryService contractCategoryService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get All Contract Categories", authorizations = {@Authorization("JWT")})
	@GetMapping( value = "/contractcategory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContractCategoryPOJO>> getAllContractCategorys() {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
				.body(contractCategoryService.fetchContractCategoryTenantId(tenantId));
	}
	
	/**
	 * @param contractCategoryId
	 * @return
	 */
	@ApiOperation(value = "Get Contract Category by Id", authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Category Returned!", response = ContractCategoryPOJO.class),
			@ApiResponse(code = 404, message = "Contract Category does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contractcategory/{id}")
	public ResponseEntity<?> fetchContractCategoryById(@PathVariable(value = "id") String contractCategoryId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(contractCategoryService.isExist(contractCategoryId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(contractCategoryService.fetchContractCategoryId(contractCategoryId));
		}
		throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
	}
	
	/**
	 * @param contractCategoryCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Contract Category", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Category Name!")
	})
	@PostMapping( value = "/contractcategory/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContractCategory(@RequestBody @Valid ContractCategoryCreateDTO contractCategoryCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = contractCategoryService.isExist(contractCategoryCreateDTO, tenantId);
		if(! isExist) {
			contractCategoryService.createContractCategory(contractCategoryCreateDTO, tenantId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Contract Category Name!", "Duplicate Contract Category Name!");
	}
	
	
	/**
	 * @param contractCategoryId
	 * @param contractCategoryCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Contract Category by Id", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Contract Category does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Category Name!")
	})
	@PutMapping( value = "/contractcategory/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContractCategory(@PathVariable(value = "id") String contractCategoryId,
			@RequestBody ContractCategoryCreateDTO contractCategoryCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = contractCategoryService.isExist(contractCategoryId, tenantId);
		if(isExist) {
			boolean isExistNew = contractCategoryService.isExist(contractCategoryCreateDTO, tenantId);
			if(! isExistNew ) {
				contractCategoryService.updateContractCategory(contractCategoryId, contractCategoryCreateDTO);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Contract Category Name!", "Duplicate Contract Category Name!");
			}
		}
		throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
	}	
}
