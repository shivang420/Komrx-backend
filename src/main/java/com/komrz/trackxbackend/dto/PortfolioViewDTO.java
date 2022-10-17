package com.komrz.trackxbackend.dto;

public class PortfolioViewDTO {

	private String portfolioId;
	private String portfolioName;
	private String programName;
	private String costCenterName;
	private String projectName;
	private String totalAmount = "0";
	
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}
	public String getPortfolioName() {
		return portfolioName;
	}
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
}
