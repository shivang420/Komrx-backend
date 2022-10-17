package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.LegalEntityPOJO;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntityPOJO, String> {

	List<LegalEntityPOJO> findByTenantId(String tenantId);
	
	@Query("SELECT leg.id FROM LegalEntityPOJO leg WHERE leg.legalEntityName = ?1 AND leg.tenantId = ?2")
	String isExist(String legalName, String tenantId);
	
	@Query("SELECT leg FROM LegalEntityPOJO leg WHERE leg.id = ?1")
	LegalEntityPOJO findByLegalEntityId(String legalEntityId);

	LegalEntityPOJO findByIdAndTenantId(String legalEntityId, String tenantId);

}
