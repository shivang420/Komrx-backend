package com.komrz.trackxbackend.dto;

import java.util.ArrayList;
import java.util.List;

public class PortfolioSummaryDTO {
	private List<ProgramInfoPortfolioSummaryDTO> revenueSpend = new ArrayList<>();
	private List<ProjectInfoPortfolioSummaryDTO> projectInfo = new ArrayList<>();
	
	public List<ProgramInfoPortfolioSummaryDTO> getRevenueSpend() {
		return revenueSpend;
	}
	public void setRevenueSpend(List<ProgramInfoPortfolioSummaryDTO> revenueSpend) {
		this.revenueSpend = revenueSpend;
	}
	public List<ProjectInfoPortfolioSummaryDTO> getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(List<ProjectInfoPortfolioSummaryDTO> projectInfo) {
		this.projectInfo = projectInfo;
	}
}
