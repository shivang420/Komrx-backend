package com.komrz.trackxbackend.dto;

import java.sql.Date;

public class AllContractViewDTO {
	private String contractId;
	private String contractName;
	private String contractTypeEn;
	private Date startDate;
	private Date endDate;
	private String tcv = "0";
	private String vendorName;
	private String buySellEn;
	
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
	public String getContractTypeEn() {
		return contractTypeEn;
	}
	public void setContractTypeEn(String contractTypeEn) {
		this.contractTypeEn = contractTypeEn;
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
	public String getTcv() {
		return tcv;
	}
	public void setTcv(String tcv) {
		this.tcv = tcv;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getBuySellEn() {
		return buySellEn;
	}
	public void setBuySellEn(String buySellEn) {
		this.buySellEn = buySellEn;
	}
}
