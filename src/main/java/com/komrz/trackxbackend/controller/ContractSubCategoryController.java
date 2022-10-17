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

import com.komrz.trackxbackend.dto.ContractSubCategoryCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.ContractSubCategoryPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ContractSubCategoryService;
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
public class ContractSubCategoryController {

	@Autowired
	ContractSubCategoryService contractSubCategoryService;
	
	@Autowired
	ContractCategoryService contractCategoryService;
	
	/**
	 * 
	 * @param contractCategoryId
	 * @return
	 */
	@ApiOperation(value = "Get All Contract Sub Category By Contract Category Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Contract Sub Category Returned!", response = ContractSubCategoryPOJO.class),
			@ApiResponse(code = 404, message = "Contract Category does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contractSubCategory")
	public ResponseEntity<?> getAllContractSubCategorys(@RequestParam(value = "ContractCategoryId") String contractCategoryId) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(contractCategoryService.isExist(contractCategoryId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(contractSubCategoryService.getAllContractSubCategory(contractCategoryId));
		}
		throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
	}
	
	/**
	 * 
	 * @param contractSubCategoryId
	 * @return
	 */
	@ApiOperation(value = "Get Contract Sub Category Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Sub Category Returned!", response = ContractSubCategoryPOJO.class),
			@ApiResponse(code = 404, message = "Contract Sub Category does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contractSubCategory/{id}")
	public ResponseEntity<?> fetchContractSubCategoryById(@PathVariable(value = "id") String contractSubCategoryId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		ContractSubCategoryPOJO contractSubCategory = contractSubCategoryService.fetchContractSubCategoryId(contractSubCategoryId);
		String contractCategoryId = contractSubCategory.getContractCategoryId();
		if(contractCategoryService.isExist(contractCategoryId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(contractSubCategory);
		}
		throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
	}
	
	/**
	 * 
	 * @param contractSubCategoryCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Contract Sub Category", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Contract Category does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Sub Category Name!")
	})
	@PostMapping( value = "/contractSubCategory/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createContractSubCategory(@RequestBody @Valid ContractSubCategoryCreateDTO contractSubCategoryCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExistContractCategory = contractCategoryService.isExist(contractSubCategoryCreateDTO.getContractCategoryId(), tenantId);
		if(isExistContractCategory == false) {
			throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
		}
		boolean isExist = contractSubCategoryService.isExist(contractSubCategoryCreateDTO, contractSubCategoryCreateDTO.getContractCategoryId());
		if(isExist == false) {
			contractSubCategoryService.createContractSubCategory(contractSubCategoryCreateDTO, tenantId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Contract Sub Category Name!", "Duplicate Contract Sub Category Name!");
	}
	
	/**
	 * 
	 * @param contractSubCategoryId
	 * @param contractSubCategoryCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Contract Sub Category", code = 201, authorizations = {@Authorization("JWT")})
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Contract Sub Category does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Sub Category Name!")
	})
	@PutMapping( value = "/contractSubCategory/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContractSubCategory(@PathVariable(value = "id") String contractSubCategoryId,
			@RequestBody ContractSubCategoryCreateDTO contractSubCategoryCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		ContractSubCategoryPOJO contractSubCategory = contractSubCategoryService.fetchContractSubCategoryId(contractSubCategoryId);
		if(contractSubCategory == null) {
			throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
		}
		boolean isExist = contractCategoryService.isExist(contractSubCategory.getContractCategoryId(), tenantId);
		if(isExist == true) {
			contractSubCategoryCreateDTO.setContractCategoryId(contractSubCategory.getContractCategoryId());
			boolean isExistNew = contractSubCategoryService.isExist(contractSubCategoryCreateDTO, contractSubCategory.getContractCategoryId());
			if(isExistNew == false) {
				contractSubCategoryService.updateContractSubCategory(contractSubCategoryId, contractSubCategoryCreateDTO);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Contract Sub Category Name!", "Duplicate Contract Sub Category Name!");
			}
		}
		throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
	}	
}
