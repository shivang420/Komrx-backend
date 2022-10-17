package com.komrz.trackxbackend.dto;

public class ProgramInfoPortfolioSummaryDTO {

	private String programName;
	private String buySell;
	private double amount;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getBuySell() {
		return buySell;
	}
	public void setBuySell(String string) {
		this.buySell = string;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
}
