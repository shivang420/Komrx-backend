package com.komrz.trackxbackend.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.enumerator.ContractFormatEn;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;
import com.komrz.trackxbackend.enumerator.ContractTypeEn;

public class ContractCreateDTO {
	
	@NotBlank(message = "Contract Name cannot be blank!")
	private String contractName;
	
	private ContractStatusEn contractStatus;

	@NotBlank(message = "Vendor Id cannot be blank!")
	private String vendorId;
	
	private String wbsId;
	
	@NotBlank(message = "Cost Center Id cannot be blank!")
	private String costCenterId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractStartDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractEndDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractRenewalDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date contractTerminationDate;
	
	private String vendorManager;
	private String businessPartnerName;
	private ContractFormatEn contractFormatEn;
	private ContractTypeEn contractTypeEn;
	
	@NotBlank(message = "Project Id cannot be blank!")
	private String projectId;
	private String notes;
	
	@NotBlank(message = "Contract Sub Category Id cannot be blank!")
	private String contractSubCategoryId;
	
	@NotBlank(message = "Contract Category Id cannot be blank!")
	private String contractCategoryId;
	private BuySellEn buySellEn;
	
//	private List<ContractBillInfoCreateDTO> contractBillInfoCreateDTOList;
	private String contractBillInfo;
	
	//getters and setters
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public ContractStatusEn getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(ContractStatusEn contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getWbsId() {
		return wbsId;
	}
	public void setWbsId(String wbsId) {
		this.wbsId = wbsId;
	}
	public String getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}
	public Date getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public Date getContractRenewalDate() {
		return contractRenewalDate;
	}
	public void setContractRenewalDate(Date contractRenewalDate) {
		this.contractRenewalDate = contractRenewalDate;
	}
	public Date getContractTerminationDate() {
		return contractTerminationDate;
	}
	public void setContractTerminationDate(Date contractTerminationDate) {
		this.contractTerminationDate = contractTerminationDate;
	}
	public String getVendorManager() {
		return vendorManager;
	}
	public void setVendorManager(String vendorManager) {
		this.vendorManager = vendorManager;
	}
	public String getBusinessPartnerName() {
		return businessPartnerName;
	}
	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}
	public ContractFormatEn getContractFormatEn() {
		return contractFormatEn;
	}
	public void setContractFormatEn(ContractFormatEn contractFormatEn) {
		this.contractFormatEn = contractFormatEn;
	}
	public ContractTypeEn getContractTypeEn() {
		return contractTypeEn;
	}
	public void setContractTypeEn(ContractTypeEn contractTypeEn) {
		this.contractTypeEn = contractTypeEn;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getContractSubCategoryId() {
		return contractSubCategoryId;
	}
	public String getContractCategoryId() {
		return contractCategoryId;
	}
	public BuySellEn getBuySellEn() {
		return buySellEn;
	}
	public void setContractSubCategoryId(String contractSubCategoryId) {
		this.contractSubCategoryId = contractSubCategoryId;
	}
	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}
	public void setBuySellEn(BuySellEn buySellEn) {
		this.buySellEn = buySellEn;
	}
	public String getContractBillInfo() {
		return contractBillInfo;
	}
	public void setContractBillInfo(String contractBillInfo) {
		this.contractBillInfo = contractBillInfo;
	}
}
