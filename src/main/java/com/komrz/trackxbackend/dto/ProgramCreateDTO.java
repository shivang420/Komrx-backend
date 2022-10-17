package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class ProgramCreateDTO {

	@NotBlank(message = "Program Name cannot be blank!")
	private String programName;
	
	@NotBlank(message = "Portfolio Id cannot be blank!")
	private String portfolioId;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}
}
