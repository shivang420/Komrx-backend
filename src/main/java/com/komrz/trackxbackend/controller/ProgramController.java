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

import com.komrz.trackxbackend.dto.ProgramCreateDTO;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.ProgramPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.PortfolioService;
import com.komrz.trackxbackend.service.ProgramService;

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
public class ProgramController {

	@Autowired
	ProgramService programService;
	
	@Autowired
	PortfolioService portfolioService;
	
	/**
	 * 
	 * @param portfolioId
	 * @return
	 */
	@ApiOperation(value = "Get All Programs By Portfolio Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Programs Returned!", response = ProgramPOJO.class),
			@ApiResponse(code = 404, message = "Portfolio does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/program")
	public ResponseEntity<?> getAllPrograms(@RequestParam("PortfolioId") String portfolioId) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(portfolioService.isExist(portfolioId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(programService.getAllProgram(portfolioId));
		}
		throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
	}
	
	/**
	 * 
	 * @param programId
	 * @return
	 */
	@ApiOperation(value = "Get Program Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Program Returned!", response = ProgramPOJO.class),
			@ApiResponse(code = 404, message = "Program does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/program/{id}")
	public ResponseEntity<?> fetchProgramById(@PathVariable(value = "id") String programId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		ProgramPOJO program = programService.fetchProgramId(programId);
		if(program == null) {
			throw new NotFoundException("Program does not exists!", "Program does not exists!");
		}
		if(tenantId.equals(program.getPortfolio().getTenantId())) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(programService.fetchProgramId(programId));
		}
		throw new NotFoundException("Program does not exists!", "Program does not exists!");
	}
	
	/**
	 * 
	 * @param programCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Program", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Portfolio does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Program Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/program/new", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createProgram(@RequestBody @Valid ProgramCreateDTO programCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		boolean isExist = portfolioService.isExist(programCreateDTO.getPortfolioId(), tenantId);
		if(isExist == false) {
			throw new NotFoundException("Portfolio does not exists!", "Portfolio does not exists!");
		}
		boolean isExistProgram = programService.isExist(programCreateDTO, tenantId);
		if(isExistProgram == false) {
			programService.createProgram(programCreateDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
		}
		throw new ConflictException("Duplicate Program Name!", "Duplicate Program Name!");
	}
	
	/**
	 * 
	 * @param programId
	 * @param programCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Program", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Program does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Program Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/program/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProgram(@PathVariable(value = "id") String programId,
											@RequestBody @Valid ProgramCreateDTO programCreateDTO) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		ProgramPOJO program = programService.fetchProgramId(programId);
		if(program == null) {
			throw new NotFoundException("Program does not exists!", "Program does not exists!");
		}
		if(tenantId.equals(program.getPortfolio().getTenantId())) {
			boolean isExistNew = programService.isExist(programCreateDTO, tenantId);
			if(isExistNew == false) {
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(programService.updateProgram(programId, programCreateDTO));
			}
			else {
				throw new ConflictException("Duplicate Program Name!", "Duplicate Program Name!");
			}
		}
		throw new NotFoundException("Program does not exists!", "Program does not exists!");
	}	
}
