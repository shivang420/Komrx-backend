package com.komrz.trackxbackend.dto;

public class RenewalContractCalendarViewDTO {

	private String contractName;
	private String vendorName;
	private String renewalDate;
	private String amount = "0";
	
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
	public String getRenewalDate() {
		return renewalDate;
	}
	public void setRenewalDate(String renewalDate) {
		this.renewalDate = renewalDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
