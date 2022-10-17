package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.komrz.trackxbackend.model.ContractCategoryPOJO;

public interface ContractCategoryRepository extends JpaRepository<ContractCategoryPOJO, String> {

	List<ContractCategoryPOJO> findByTenantId(String tenantId);	
	
	@Query("SELECT cc.id FROM ContractCategoryPOJO cc WHERE cc.contractCategoryName = ?1 AND cc.tenantId = ?2")
	String isExist(String contractCategoryName, String tenantId);
	
	@Query("SELECT cc FROM ContractCategoryPOJO cc WHERE cc.id = ?1")
	ContractCategoryPOJO findByContractCategoryId(String contractCategoryId);

	ContractCategoryPOJO findByIdAndTenantId(String contractCategoryId, String tenantId);
}
