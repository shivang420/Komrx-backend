package com.komrz.trackxbackend.dto;

public class YoyComparisonProcurementViewDTO {

	private String buySell;
	private String amount = "0";
	
	public String getBuySell() {
		return buySell;
	}
	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
