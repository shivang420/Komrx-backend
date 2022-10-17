package com.komrz.trackxbackend.dto;

public class ProjectInfoPortfolioSummaryDTO {

	private String programName;
	private String projectName;
	private double spendAmount;
	private double revenueAmount;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public double getSpendAmount() {
		return spendAmount;
	}
	public void setSpendAmount(double spendAmount) {
		this.spendAmount = spendAmount;
	}
	public double getRevenueAmount() {
		return revenueAmount;
	}
	public void setRevenueAmount(double revenueAmount) {
		this.revenueAmount = revenueAmount;
	}
}
