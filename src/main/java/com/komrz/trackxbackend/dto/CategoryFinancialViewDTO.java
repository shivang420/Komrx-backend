package com.komrz.trackxbackend.dto;

public class CategoryFinancialViewDTO {

	private String contractCategoryName;
	private String billQuarter;
	private String amount = "0";
	
	public String getContractCategoryName() {
		return contractCategoryName;
	}
	public void setContractCategoryName(String contractCategoryName) {
		this.contractCategoryName = contractCategoryName;
	}
	public String getBillQuarter() {
		return billQuarter;
	}
	public void setBillQuarter(String billQuarter) {
		this.billQuarter = billQuarter;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
