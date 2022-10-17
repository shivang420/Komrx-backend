package com.komrz.trackxbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.komrz.trackxbackend.dto.TemplatesDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.TemplatesService;

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
@RequestMapping("/templates")
public class TemplatesController {

	@Autowired
	private TemplatesService templatesService;
	
	@ApiOperation(value = "Get All Template Contracts", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List Of Template Contracts Returned!", response = TemplatesDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contracts/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTemplateContractsByTenant() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
				.body(templatesService.getAllTemplateContracts(tenantId));
	}
	
	@ApiOperation(value = "Upload Template Contract", code = 201, authorizations = {@Authorization("JWT")})
	@PostMapping(value = "/contracts/upload")
	public ResponseEntity<?> uploadTemplateContract(@RequestParam(name = "name")String templateName,
													@RequestParam MultipartFile templateFile){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(templatesService.isExistContractByName(templateName, tenantId)) {
			throw new ConflictException("Duplicate Template Name!", "Template with same name already exists");
		}
		templatesService.uploadTemplateContract(templateFile, templateName, tenantId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Uploaded!"));
	}
	
	@ApiOperation(value = "Delete Template Contract", code = 201, authorizations = {@Authorization("JWT")})
	@DeleteMapping(value = "/contracts/{id}/delete")
	public ResponseEntity<?> deleteTemplateContract( @PathVariable(value = "id")String id){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(templatesService.isExistContractForDelete(id, tenantId)) {
			templatesService.deleteContract(id);
			SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Template successfully deleted!");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		}
		throw new NotFoundException("Template Cannot be deleted!", "Template Cannot be deleted!");
	}
	
	@ApiOperation(value = "Get All Template Guides", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List Of Template Guides Returned!", response = TemplatesDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/guides/all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllTemplateGuidesByTenant() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
				.body(templatesService.getAllTemplateGuides(tenantId));
	}
	
	@ApiOperation(value = "Upload Template Guide", code = 201, authorizations = {@Authorization("JWT")})
	@PostMapping(value = "/guides/upload")
	public ResponseEntity<?> uploadTemplateGuides(@RequestParam(name = "name")String templateName,
													@RequestParam MultipartFile templateFile){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(templatesService.isExistGuideByName(templateName, tenantId)) {
			throw new ConflictException("Duplicate Template Name!", "Template with same name already exists");
		}
		templatesService.uploadTemplateGuide(templateFile, templateName, tenantId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Uploaded!"));
	}
	
	@ApiOperation(value = "Delete Template Guide", code = 201, authorizations = {@Authorization("JWT")})
	@DeleteMapping(value = "/guides/{id}/delete")
	public ResponseEntity<?> deleteTemplateGuide( @PathVariable(value = "id")String id){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(templatesService.isExistGuideForDelete(id, tenantId)) {
			templatesService.deleteGuide(id);
			SuccessResponse response = new SuccessResponse(HttpStatus.OK, "Template successfully deleted!");
			return ResponseEntity.status(HttpStatus.OK)
					.body(response);
		}
		throw new NotFoundException("Template Cannot be deleted!", "Template Cannot be deleted!");
	}
}
