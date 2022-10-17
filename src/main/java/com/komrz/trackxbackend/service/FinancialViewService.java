package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.CategoryFinancialViewDTO;
import com.komrz.trackxbackend.dto.CostCenterFinancialViewDTO;
import com.komrz.trackxbackend.dto.FinancialTrendFinancialViewFetchDTO;
import com.komrz.trackxbackend.dto.InfoFinancialViewDTO;
import com.komrz.trackxbackend.dto.ProgramFinancialViewDTO;
import com.komrz.trackxbackend.dto.TopContractFinancialViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.repository.FinancialViewRepository;

@Service
public class FinancialViewService {
	
	@Autowired
	private FinancialViewRepository financialViewRepository;
	
	public List<TopContractFinancialViewDTO> topContractFinancialView(String tenantId, BuySellEn buySell, String startDate, String endDate) {
		return financialViewRepository.topContractFinancialView(tenantId, buySell, startDate, endDate);
	}
	
	public List<FinancialTrendFinancialViewFetchDTO> financialTrendFinancialView(String tenantId, String year){
		return financialViewRepository.financialTrendFinancialView(tenantId, year);
	}
	
	public List<CategoryFinancialViewDTO> categoryFinancialView(String tenantId, BuySellEn buySell, String year){
		return financialViewRepository.categoryFinancailView(tenantId, buySell, year);
	}
	
	public List<ProgramFinancialViewDTO> programFinancialView(String tenantId, String portfolioId, BuySellEn buySell, String year){
		return financialViewRepository.programFinancialView(tenantId, portfolioId, buySell, year);
	}
	
	public List<CostCenterFinancialViewDTO> costCenterFinancialView(String tenantId, String legalEntityId, BuySellEn buySell, String year){
		return financialViewRepository.costCenterFinancialView(tenantId, legalEntityId, buySell, year);
	}
	
	public InfoFinancialViewDTO infoFinancialView(String tenantId, String year) {
		InfoFinancialViewDTO info = new InfoFinancialViewDTO();
		info.setTotalActiveContract(financialViewRepository.totalContractFinancialView(tenantId));
		info.setRevenue(financialViewRepository.revenueAndSpendFinancialView(tenantId, "sell", year));
		info.setSpend(financialViewRepository.revenueAndSpendFinancialView(tenantId, "buy", year));
		return info;
	}
}
