package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.CostCenterPOJO;

@Repository
public interface CostCenterRepository extends JpaRepository<CostCenterPOJO, String> {

	List<CostCenterPOJO> findByLegalEntityId(String legalEntityId);
	
	@Query("SELECT cc.id FROM CostCenterPOJO cc WHERE cc.costCenterName = ?1 AND cc.legalEntity.id = ?2")
	String isExist(String costCenterName, String legalEntityId);
	
	@Query("SELECT cc FROM CostCenterPOJO cc WHERE cc.id = ?1")
	CostCenterPOJO findByCostCenterId(String costCenterId);

	List<CostCenterPOJO> findByCostCenterName(String costCenterName);
}
