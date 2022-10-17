package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.VendorPOJO;

@Repository
public interface VendorRepository extends JpaRepository<VendorPOJO , String> {
	
	List<VendorPOJO> findByTenantId(String tenantId);

	@Query("SELECT v.id FROM VendorPOJO v WHERE v.vendorName = ?1 AND v.tenantId = ?2")
	String isExist(String vendorName, String tenantId);
	
	@Query("SELECT v FROM VendorPOJO v WHERE v.id = ?1")
	VendorPOJO findByVendorId(String vendorId);

	List<VendorPOJO> findByIdAndTenantId(String vendorId, String tenantId);
}
