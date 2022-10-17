package com.komrz.trackxbackend.dto;

public class BillInfoContractSummaryDTO {
	private String billDate;
	private String amount = "0";
	
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
