package com.komrz.trackxbackend.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.komrz.trackxbackend.dto.ContractBillInfoCreateDTO;
import com.komrz.trackxbackend.dto.ContractCreateDTO;
import com.komrz.trackxbackend.dto.ContractFetchDTO;
import com.komrz.trackxbackend.enumerator.BillPaymentTypeEn;
import com.komrz.trackxbackend.enumerator.ContractFormatEn;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;
import com.komrz.trackxbackend.enumerator.ContractTypeEn;
import com.komrz.trackxbackend.exception.BadRequestException;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.ContractPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.ContractBillInfoService;
import com.komrz.trackxbackend.service.ContractCategoryService;
import com.komrz.trackxbackend.service.ContractService;
import com.komrz.trackxbackend.service.ContractSubCategoryService;
import com.komrz.trackxbackend.service.CostCenterService;
import com.komrz.trackxbackend.service.ProjectService;
import com.komrz.trackxbackend.service.VendorService;
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
public class ContractController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ContractController.class);

	@Autowired
	private ContractService contractService;
	
	@Autowired
	private ContractBillInfoService contractBillInfoService;
	
	@Autowired
	private CostCenterService costCenterService;
	
	@Autowired
	private WbsService wbsService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ContractCategoryService contractCategoryService;
	
	@Autowired
	private ContractSubCategoryService contractSubCategoryService;
	
	@Autowired
	private VendorService vendorService;
	
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Contract Format Types", code = 200)
	@GetMapping(value = "/contract/format")
	public ResponseEntity<?> getContractFormatTypes(){
		return ResponseEntity.ok(ContractFormatEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Contract Types", code = 200)
	@GetMapping(value = "/contract/type")
	public ResponseEntity<?> getContractTypes(){
		return ResponseEntity.ok(ContractTypeEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Contract Status En", code = 200)
	@GetMapping(value = "/contract/status")
	public ResponseEntity<?> getContractStatusEn(){
		return ResponseEntity.ok(ContractStatusEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Bill Payment Type En", code = 200)
	@GetMapping(value = "/contract/paymenttype")
	public ResponseEntity<?> getBillPaymentType(){
		return ResponseEntity.ok(BillPaymentTypeEn.values());
	}
	
	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get All Contracts", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List Of Contracts Returned!", response = ContractFetchDTO.class)
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contract", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContractFetchDTO>> getAllContractByTenant() {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(contractService.fetchContractTenantId(tenantId));
	}
	
	/**
	 * 
	 * @param contractId
	 * @return
	 */
	@ApiOperation(value = "Get Contract Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Contract Returned!", response = ContractPOJO.class),
			@ApiResponse(code = 404, message = "Contract does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/contract/{id}")
	public ResponseEntity<?> fetchContractById(@PathVariable(value = "id") String contractId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(contractService.isExist(contractId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(contractService.fetchContractId(contractId));
		}
		throw new NotFoundException("Contract does not exists!", "Contract does not exists!");
	}
	
	/**
	 * 	
	 * @param contractCreateDTO
	 * @param contractFile
	 * @return
	 */
	@ApiOperation(value = "Add New Contract", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Input Values does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/contract/new/")
//	@CrossOrigin(origins = "http://localhost:3000")
	public ResponseEntity<?> createContract(@ModelAttribute @Valid ContractCreateDTO contractCreateDTO, 
													@RequestParam MultipartFile contractFile,
													@RequestParam(required = false) MultipartFile[] supportingFiles) {
		LOG.info("Enetred ContractController -> CreateContract" );
		LOG.info("Contract File Name" + contractFile.getName() + "File Size" + contractFile.getSize());
		LOG.info("Number of Supporting Documents: " + supportingFiles.length);
		LOG.debug("Entered ContractController --> createContract");
		LOG.info("Upload Supporting Documents :" + supportingFiles.length);
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String userId = userPrincipal.getUsername();
		String tenantId = userPrincipal.getTenantId();
		ObjectMapper mapper = new ObjectMapper();
		List<ContractBillInfoCreateDTO> billInfo = new ArrayList<>();
		try {
			billInfo = Arrays.asList(mapper.readValue(contractCreateDTO.getContractBillInfo(), ContractBillInfoCreateDTO[].class));
		} catch (JsonProcessingException e1) {
			throw new BadRequestException("Contract Bill Info Not properly formed!", e1.getMessage());
		}
		if(! vendorService.isExist(contractCreateDTO.getVendorId(), tenantId)) {
			throw new NotFoundException("Vendor does not exists!", "Vendor does not exists!");
		}
		String costCenterId = null;
		if(contractCreateDTO.getWbsId() != null) {
			if(! wbsService.isExist(contractCreateDTO.getWbsId(), tenantId)) {
				throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
			}else {
				costCenterId = wbsService.fetchWbsId(contractCreateDTO.getWbsId()).getCostCenter().getId();
			}
		}
		if(! costCenterService.isExist(contractCreateDTO.getCostCenterId(), tenantId)) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if( costCenterId != null) {
			if(! costCenterId.equals(contractCreateDTO.getCostCenterId())) {
				throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
			}
		}
		if(! projectService.isExist(contractCreateDTO.getProjectId(), tenantId)) {
			throw new NotFoundException("Project does not exists!", "Project does not exists!");
		}
		String contractCategoryId = null;
		if(! contractSubCategoryService.isExist(contractCreateDTO.getContractSubCategoryId(), tenantId)) {
			throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
		}
		contractCategoryId = contractSubCategoryService.fetchContractSubCategoryId(contractCreateDTO.getContractSubCategoryId()).getContractCategoryId();
		if(! contractCategoryId.equals(contractCreateDTO.getContractCategoryId())) {
			throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
		}
		if(! contractCategoryService.isExist(contractCreateDTO.getContractCategoryId(), tenantId)) {
			throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
		}
		boolean isExist = contractService.isExistByName(contractCreateDTO.getContractName(), tenantId);
		if(! isExist ) {
			String contractId = contractService.createContract(contractCreateDTO, userId, tenantId, supportingFiles.length);
			LOG.info("Contract Id: " + contractId );
			contractBillInfoService.createContractBillList(billInfo, contractId, tenantId);
			LOG.info("Contract Bill Info Uploaded");
			contractService.uploadContract(contractFile, contractId);
			LOG.info("Contarct File Uplaoded");
			if(supportingFiles.length != 0) {
				try {
					contractService.uploadSupportingDocument(supportingFiles, contractId);
					LOG.info("Supporting Documents Uploaded");
				} catch (IllegalStateException | IOException e) {
					throw new BadRequestException("Bad Request", e.getMessage());
				}
			LOG.info("Exiting Contract Controller -> CreateContract with ContractId:" + contractId);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
			}
		}
		throw new ConflictException("Duplicate Contract Name!", "Duplicate Contract Name!");
	}
	
	@ApiOperation(value = "Fetch Contract Document", code = 200, authorizations = {@Authorization("JWT")}) 
	@GetMapping(value = "/contract/{id}/fetch")
	public ResponseEntity<?> fetchContract(@PathVariable(value = "id") String contractId){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(! contractService.isExist(contractId, tenantId)) {
			throw new NotFoundException("Contract does not exists!", "Contract does not exists!");
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(contractService.fetchContractDocument(contractId));
	}
	
	/**
	 * 
	 * @param contractId
	 * @param contractCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Update Contract", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Updated!"),
			@ApiResponse(code = 404, message = "Contract does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Contract Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PutMapping( value = "/contract/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContract(@PathVariable(value = "id") String contractId,
												@RequestBody @Valid ContractCreateDTO contractCreateDTO) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String userId = userPrincipal.getId();
		String tenantId = userPrincipal.getTenantId();
		if(! vendorService.isExist(contractCreateDTO.getVendorId(), tenantId)) {
			throw new NotFoundException("Vendor does not exists!", "Vendor does not exists!");
		}
		String costCenterId = null;
		if(contractCreateDTO.getWbsId() != null) {
			if(! wbsService.isExist(contractCreateDTO.getWbsId(), tenantId)) {
				throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
			}else {
				costCenterId = wbsService.fetchWbsId(contractCreateDTO.getWbsId()).getCostCenter().getId();
			}
		}
		if(! costCenterService.isExist(contractCreateDTO.getCostCenterId(), tenantId)) {
			throw new NotFoundException("Cost Center does not exists!", "Cost Center does not exists!");
		}
		if( costCenterId != null) {
			if(! costCenterId.equals(contractCreateDTO.getCostCenterId())) {
				throw new NotFoundException("Wbs does not exists!", "Wbs does not exists!");
			}
		}
		if(! projectService.isExist(contractCreateDTO.getProjectId(), tenantId)) {
			throw new NotFoundException("Project does not exists!", "Project does not exists!");
		}
		String contractCategoryId = null;
		if(! contractSubCategoryService.isExist(contractCreateDTO.getContractSubCategoryId(), tenantId)) {
			throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
		}
		contractCategoryId = contractCreateDTO.getContractSubCategoryId();
		if(! contractCategoryId.equals(contractCreateDTO.getContractCategoryId())) {
			throw new NotFoundException("Contract Sub Category does not exists!", "Contract Sub Category does not exists!");
		}
		if(! contractCategoryService.isExist(contractCreateDTO.getContractCategoryId(), tenantId)) {
			throw new NotFoundException("Contract Category does not exists!", "Contract Category does not exists!");
		}
		boolean isExist = contractService.isExist(contractId, tenantId);
		if(isExist) {
			boolean isExistNew = contractService.isExistByName(contractCreateDTO.getContractName(), tenantId);
			if(! isExistNew) {
				contractService.updateContract(contractId, contractCreateDTO, userId);
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Updated!"));
			}
			else {
				throw new ConflictException("Duplicate Contract Name!", "Duplicate Contract Name!");
			}
		}
		throw new NotFoundException("Contract does not exists!", "Contract does not exists!");
	}	
}
