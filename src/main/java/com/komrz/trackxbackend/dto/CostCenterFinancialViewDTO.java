package com.komrz.trackxbackend.dto;

public class CostCenterFinancialViewDTO {

	private String costCenterName;
	private String amount = "0";
	
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
