package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.PortfolioPOJO;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioPOJO, String> {

	List<PortfolioPOJO> findByTenantId(String tenantId);
	
	@Query("SELECT p.id FROM PortfolioPOJO p WHERE p.name = ?1 AND p.tenantId = ?2")
	String isExist(String portfolioName, String tenantId);
	
	@Query("SELECT p FROM PortfolioPOJO p WHERE p.id = ?1")
	PortfolioPOJO findByPortfolioId(String portfolioId);

	List<PortfolioPOJO> findByIdAndTenantId(String portfolioId, String tenantId);
}
