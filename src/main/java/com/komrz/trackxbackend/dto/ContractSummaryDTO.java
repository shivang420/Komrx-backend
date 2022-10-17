package com.komrz.trackxbackend.dto;

import java.util.ArrayList;
import java.util.List;

public class ContractSummaryDTO {

	private String contractId;
	private String contractName;
	private String notes;
	private String startDate;
	private String endDate;
	private String renewalDate;
	private String terminationDate;
	private String vendorName;
	private String vendorAddress;
	private String vendorContactName;
	private String phone;
	private String projectName;
	private String programName;
	private String costCenterName;
	private String legalEntityName;
	private List<BillInfoContractSummaryDTO> billInfo = new ArrayList<>();
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorAddress() {
		return vendorAddress;
	}
	public void setVendorAddress(String vendorAddress) {
		this.vendorAddress = vendorAddress;
	}
	public String getVendorContactName() {
		return vendorContactName;
	}
	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getLegalEntityName() {
		return legalEntityName;
	}
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
	public List<BillInfoContractSummaryDTO> getBillInfo() {
		return billInfo;
	}
	public void setBillInfo(List<BillInfoContractSummaryDTO> billInfo) {
		this.billInfo = billInfo;
	}
}
