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

import com.komrz.trackxbackend.dto.ProjectCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.ProjectPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ProgramService;
import com.komrz.trackxbackend.service.ProjectService;

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
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	@Autowired
	ProgramService programService;
	
	/**
	 * 
	 * @param programId
	 * @return
	 */
	@ApiOperation(value = "Get All Projects By Program Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Projects Returned!", response = ProjectPOJO.class),
			@ApiResponse(code = 404, message = "Program does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/project")
	public ResponseEntity<?> getAllProjects(@RequestParam("ProgramId") String programId) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(programService.isExist(programId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(projectService.getAllProject(programId));
		}
		throw new NotFoundException("Program does not exists!", "Program does not exists!");
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	@ApiOperation(value = "Get Project Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Project Returned!", response = ProjectPOJO.class),
			@ApiResponse(code = 404, message = "Project does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/project/{id}")
	public ResponseEntity<?> fetchProjectById(@PathVariable(value = "id") String projectId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(projectService.isExist(projectId, tenantId)) {
			throw new NotFoundException("Project does not exists!", "Project does not exists!");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(projectService.fetchProjectId(projectId));
	}
	
	/**
	 * 
	 * @param projectCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Project", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Program does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Project Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/project/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProject(@RequestBody @Valid ProjectCreateDTO projectCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(programService.isExist(projectCreateDTO.getProgramId(), tenantId)) {
			if(projectService.isExist(projectCreateDTO, tenantId)) {
				throw new ConflictException("Duplicate Project Name!", "Duplicate Project Name!");
			}
			projectService.createProject(projectCreateDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new NotFoundException("Program does not exists!", "Program does not exists!");
	}
	
	/**
	 * 
	 * @param projectId
	 * @param projectCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Project", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Project does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Project Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/project/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateProject(@PathVariable(value = "id") String projectId,
											@RequestBody @Valid ProjectCreateDTO projectCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		ProjectPOJO project = projectService.fetchProjectId(projectId);
		if (project == null) {
			throw new NotFoundException("Program does not exists!", "Program does not exists!");
		}
		if(tenantId.equals(project.getProgram().getPortfolio().getTenantId())) {
			if(projectService.isExist(projectCreateDTO, tenantId)) {
				throw new ConflictException("Duplicate Project Name!", "Duplicate Project Name!");
			}
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(projectService.updateProject(projectId, projectCreateDTO));
		}
		throw new NotFoundException("Program does not exists!", "Program does not exists!");
	}	
}
