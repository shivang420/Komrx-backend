package com.komrz.trackxbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ContractSummaryDTO;
import com.komrz.trackxbackend.dto.PortfolioSummaryDTO;
import com.komrz.trackxbackend.dto.VendorSummaryDTO;
import com.komrz.trackxbackend.repository.ContractSummaryRepository;
import com.komrz.trackxbackend.repository.PortfolioSummaryRepository;

@Service
public class ReportsService {

	@Autowired
	private ContractSummaryRepository contractSummaryRepository;
	
	@Autowired
	private PortfolioSummaryRepository portfolioSummaryRepository;
	
	public ContractSummaryDTO contractSummary(String contractId) {
		ContractSummaryDTO con = contractSummaryRepository.contractMetaData(contractId);
		con.setBillInfo(contractSummaryRepository.billInfo(contractId));
		return con;
	}
	
	public VendorSummaryDTO vendorSummary(String vendorId) {
		
		
		return null;
	}
	
	public PortfolioSummaryDTO portfolioSummary(String portfolioId) {
		PortfolioSummaryDTO response = new PortfolioSummaryDTO();
		response.setRevenueSpend(portfolioSummaryRepository.revenueSpend(portfolioId));
		response.setProjectInfo(portfolioSummaryRepository.projectInfo(portfolioId));
		return response;
	}
}
