package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.ContractBillInfoPOJO;

@Repository
public interface ContractBillInfoRepository extends JpaRepository<ContractBillInfoPOJO, String> {

	List<ContractBillInfoPOJO> findByContractId(String contractId);
}
