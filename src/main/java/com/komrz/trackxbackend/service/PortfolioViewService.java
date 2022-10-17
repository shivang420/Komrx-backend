package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.PortfolioViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.repository.PortfolioViewRepository;

@Service
public class PortfolioViewService {

	@Autowired
	private PortfolioViewRepository portfolioViewRepository;
	
	public List<PortfolioViewDTO> topPortfolioView(String tenantId, BuySellEn buySell, String startDate, String endDate) {
		return portfolioViewRepository.topPortfolio(tenantId, buySell, startDate, endDate);
	}
}
