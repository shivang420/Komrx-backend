package com.komrz.trackxbackend.dto;

import java.util.Date;

public class ActiveContractEventProcurementView {

	private String contractName;
	private String contractType;
	private Date endDate;
	
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}	
}
