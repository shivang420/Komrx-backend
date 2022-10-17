package com.komrz.trackxbackend.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class TopContractFinancialViewDTO {

	private String contractId;
	private String contractName;
	private String vendorName;
	
	@DateTimeFormat(pattern = "YYYY-MM-DD")
	private Date startDate;
	
	@DateTimeFormat(pattern = "YYYY-MM-DD")
	private Date endDate;
	private String amount = "0";
	private String tcv = "0";
	
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
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTcv() {
		return tcv;
	}
	public void setTcv(String tcv) {
		this.tcv = tcv;
	}
}
