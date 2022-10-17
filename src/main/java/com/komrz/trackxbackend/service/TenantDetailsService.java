package com.komrz.trackxbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.TenantDetailsCreateDTO;
import com.komrz.trackxbackend.model.TenantDetailsPOJO;
import com.komrz.trackxbackend.repository.TenantDetailsRepository;

@Service
public class TenantDetailsService {

	@Autowired
	private TenantDetailsRepository tenantDetailsRepository;
	
	public String createTenant(TenantDetailsCreateDTO tenantDetailsCreateDTO) {
		TenantDetailsPOJO tenantDetails = new TenantDetailsPOJO();
		tenantDetails.setTenantName(tenantDetailsCreateDTO.getTenantName());
		tenantDetails.setStatus('Y');
		tenantDetails.setIndustryType(tenantDetailsCreateDTO.getIndustryType());
		tenantDetails.setPrefCurrency(tenantDetailsCreateDTO.getPrefCurrency());
		return tenantDetailsRepository.save(tenantDetails).getId();
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(String tenantId) {
		return tenantDetailsRepository.existsById(tenantId);
	}
	
	public String getPrefCurrency(String tenantId) {
		return tenantDetailsRepository.getPrefCurr(tenantId);
	}
	
	public String updateTenant(String tenantId, TenantDetailsCreateDTO tenantDetailsCreateDTO) {
		TenantDetailsPOJO tenantPOJO = tenantDetailsRepository.getOne(tenantId);
		tenantPOJO.setTenantName(tenantDetailsCreateDTO.getTenantName());
		tenantPOJO.setIndustryType(tenantDetailsCreateDTO.getIndustryType());
		tenantPOJO.setPrefCurrency(tenantDetailsCreateDTO.getPrefCurrency());
		
		return tenantDetailsRepository.save(tenantPOJO).getId();	
	}
	
	public TenantDetailsPOJO fetchTenantId(String tenantId) {
		return tenantDetailsRepository.getOne(tenantId);
	}
}
