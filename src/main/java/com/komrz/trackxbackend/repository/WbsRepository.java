package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.WbsPOJO;

@Repository
public interface WbsRepository extends JpaRepository<WbsPOJO , String> {
	
	List<WbsPOJO> findByCostCenterId(String costCenterId);
	
	@Query("SELECT wbs.id FROM WbsPOJO wbs WHERE wbs.wbsName = ?1 AND wbs.costCenter.id = ?2")
	String isExist(String wbsName, String costCenterId);
	
	@Query("SELECT wbs FROM WbsPOJO wbs WHERE wbs.id = ?1")
	WbsPOJO findByWbsId(String wbsId);

	List<WbsPOJO> findByWbsName(String wbsName);
}
