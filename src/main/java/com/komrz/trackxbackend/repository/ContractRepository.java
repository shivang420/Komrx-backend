package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.ContractPOJO;

@Repository
public interface ContractRepository extends JpaRepository<ContractPOJO, String> {

	List<ContractPOJO> findByTenantId(String tenantId);
	
	@Query("SELECT c.id FROM ContractPOJO c WHERE c.contractName = ?1 AND c.vendor.id = ?2")
	String isExist(String contractName, String vendorId);
	
	@Query("SELECT c FROM ContractPOJO c WHERE c.id = ?1")
	ContractPOJO findByContractId(String contractId);

	List<ContractPOJO> findByContractName(String contractName);
}
