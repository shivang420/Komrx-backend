package com.komrz.trackxbackend.dto;

public class InfoFinancialViewDTO {

	private int totalActiveContract;
	private String spend = "0";
	private String revenue = "0";
	
	public int getTotalActiveContract() {
		return totalActiveContract;
	}
	public void setTotalActiveContract(int totalActiveContract) {
		this.totalActiveContract = totalActiveContract;
	}
	public String getSpend() {
		return spend;
	}
	public void setSpend(String spend) {
		this.spend = spend;
	}
	public String getRevenue() {
		return revenue;
	}
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}	
}
