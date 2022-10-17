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

import com.komrz.trackxbackend.dto.VendorCreateDTO;
import com.komrz.trackxbackend.dto.VendorFetchDTO;
import com.komrz.trackxbackend.enumerator.VendorStatusEn;
import com.komrz.trackxbackend.enumerator.VendorTypeEn;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.VendorPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.VendorService;

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
public class VendorController {

	@Autowired
	private VendorService vendorService;
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Vendor Status En", code = 200)
	@GetMapping(value = "/vendor/status")
	public ResponseEntity<?> getVendorStatusEn(){
		return ResponseEntity.ok(VendorStatusEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Vendor Type En", code = 200)
	@GetMapping(value = "/vendor/type")
	public ResponseEntity<?> getVendorTypeEn(){
		return ResponseEntity.ok(VendorTypeEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get All Vendors", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List Of Vendors Returned!", response = VendorFetchDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/vendor", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VendorFetchDTO>> getAllVendors() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(vendorService.getAllVendorsDTO(tenantId));
	}
	
	/**
	 * 
	 * @param vendorId
	 * @return
	 */
	@ApiOperation(value = "Get Vendor Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Vendor Returned!", response = VendorPOJO.class),
			@ApiResponse(code = 404, message = "Vendor does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/vendor/{id}")
	public ResponseEntity<?> fetchVendorById(@PathVariable(value = "id") String vendorId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(vendorService.isExist(vendorId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(vendorService.fetchVendorId(vendorId));
		}
		throw new NotFoundException("Vendor does not exists!", "Vendor does not exists!");
	}
	
	/**
	 * 
	 * @param vendorCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Vendor", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Parent Vendor does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Vendor Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/vendor/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createVendor(@RequestBody @Valid VendorCreateDTO vendorCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = vendorService.isExist(vendorCreateDTO, tenantId);
		if(! isExist ) {	
			String vendorParentId = vendorCreateDTO.getVendorParentId();
			if(vendorParentId == null) {
				vendorService.createVendor(vendorCreateDTO, tenantId);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
			}
			boolean isExistParent = vendorService.isExist(vendorParentId, tenantId);
			if( isExistParent ) {
				vendorService.createVendor(vendorCreateDTO, tenantId);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
			}
			else {
				throw new NotFoundException("Parent Vendor does not exists!", "Parent Vendor does not exists!");
			}
		}
		throw new ConflictException("Duplicate Vendor Name!", "Duplicate Vendor Name!");
	}
	
	/**
	 * 
	 * @param vendorId
	 * @param vendorCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Vendor", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Vendor does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Vendor Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/vendor/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateVendor(@PathVariable(value = "id") String vendorId,
			@RequestBody @Valid VendorCreateDTO vendorCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = vendorService.isExist(vendorId, tenantId);
		if(isExist ) {
			boolean isExistNew = vendorService.isExist(vendorCreateDTO, tenantId);
			if(! isExistNew ) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(vendorService.updateVendor(vendorId, vendorCreateDTO));
			}
			else {
				throw new ConflictException("Duplicate Vendor Name!", "Duplicate Vendor Name!");
			}
		}
		throw new NotFoundException("Vendor does not exists!", "Vendor does not exists!");
	}	
}
