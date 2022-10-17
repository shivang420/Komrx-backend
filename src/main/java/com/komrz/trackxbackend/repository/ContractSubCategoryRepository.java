package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.komrz.trackxbackend.model.ContractSubCategoryPOJO;

public interface ContractSubCategoryRepository extends JpaRepository<ContractSubCategoryPOJO, String> {

	List<ContractSubCategoryPOJO> findByContractCategoryId(String contractCategoryId);
	
	@Query("SELECT cs.id FROM ContractSubCategoryPOJO cs WHERE cs.contractSubCategoryName = ?1 AND cs.contractCategoryId = ?2")
	String isExist(String contractSubCategoryName, String contractCategoryId);
	
	@Query("SELECT cs FROM ContractSubCategoryPOJO cs WHERE cs.id = ?1")
	ContractSubCategoryPOJO findByContractSubCategoryId(String contractSubCategoryId);
}
