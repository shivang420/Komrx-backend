package com.komrz.trackxbackend.dto;

public class FinancialTrendFinancialViewFetchDTO {

	private String buySell;
	private String billMonth;
	private String billAmount = "0";
	
	public String getBuySell() {
		return buySell;
	}
	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}
	public String getBillMonth() {
		return billMonth;
	}
	public void setBillMonth(String billMonth) {
		this.billMonth = billMonth;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
}
