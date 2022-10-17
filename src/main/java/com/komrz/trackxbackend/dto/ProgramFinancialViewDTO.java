package com.komrz.trackxbackend.dto;

public class ProgramFinancialViewDTO {

	private String programName;
	private String amount = "0";
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
