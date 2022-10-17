package com.komrz.trackxbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.TenantDetailsPOJO;

@Repository
public interface TenantDetailsRepository extends JpaRepository<TenantDetailsPOJO, String> {

	@Query("SELECT ten.prefCurrency FROM TenantDetailsPOJO ten WHERE ten.id = ?1")
	String getPrefCurr(String tenantId);
}
