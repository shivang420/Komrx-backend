package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.TemplateContractsPOJO;

@Repository
public interface TemplateContractsRepository extends JpaRepository<TemplateContractsPOJO, String> {

	List<TemplateContractsPOJO> findByTenantId(String tenantId);

}
