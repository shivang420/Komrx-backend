package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class PortfolioCreateDTO {
	
	@NotBlank(message = "Portfolio Name cannot be blank!")
	private String portfolioName;
	
	public String getPortfolioName() {
		return portfolioName;
	}
	public void setPortfolioName(String portfolioName) {
		this.portfolioName = portfolioName;
	}
}
