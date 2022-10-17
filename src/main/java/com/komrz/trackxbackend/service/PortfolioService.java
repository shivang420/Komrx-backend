package com.komrz.trackxbackend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.PortfolioCreateDTO;
import com.komrz.trackxbackend.enumerator.PppStatus;
import com.komrz.trackxbackend.model.PortfolioPOJO;
import com.komrz.trackxbackend.repository.PortfolioRepository;

@Service
public class PortfolioService {
	private static final Logger LOG=LoggerFactory.getLogger(PortfolioService.class);
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	public String createPortfolio(PortfolioCreateDTO portfolioCreateDTO, String tenantId) {
		LOG.info("Entered PortfolioService -> createPortfolio()");
		PortfolioPOJO newPortfolio = new PortfolioPOJO();
		newPortfolio.setName(portfolioCreateDTO.getPortfolioName());
		newPortfolio.setStatus(PppStatus.active);
		newPortfolio.setTenantId(tenantId);
		LOG.info("Exiting PortfolioService -> createPortfolio()");
		return portfolioRepository.save(newPortfolio).getId();
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String portfolioId, String tenantId) {
		List<PortfolioPOJO> portfolio = portfolioRepository.findByIdAndTenantId(portfolioId, tenantId);
		if(portfolio.isEmpty()) {
			return false;
		}
		return true;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(PortfolioCreateDTO portfolioCreateDTO, String tenantId) {
		String check = portfolioRepository.isExist(portfolioCreateDTO.getPortfolioName(), tenantId);
		if(check == null) {
			return false;
		}
		return true;
	}
	
	public String updatePortfolio(String portfolioId, PortfolioCreateDTO portfolioCreateDTO) {
		PortfolioPOJO portfolioPOJO = portfolioRepository.getOne(portfolioId);
		portfolioPOJO.setName(portfolioCreateDTO.getPortfolioName());
		
		return portfolioRepository.save(portfolioPOJO).getId();
	}

	public List<PortfolioPOJO> getAllPortfolio() {
		return portfolioRepository.findAll();
	}
	
	public PortfolioPOJO fetchPortfolioId(String portfolioId) {
		return portfolioRepository.findByPortfolioId(portfolioId);
	}
	
	public List<PortfolioPOJO> fetchPortfolioTenantId(String tenantId) {
		return portfolioRepository.findByTenantId(tenantId);	
	}
}
