package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.LegalEntityCreateDTO;
import com.komrz.trackxbackend.model.LegalEntityPOJO;
import com.komrz.trackxbackend.repository.LegalEntityRepository;

@Service
public class LegalEntityService {
	
	@Autowired
	private LegalEntityRepository legalEntityRepository;
	
	public String createLegalEntity(LegalEntityCreateDTO legalEntityCreateDTO, String tenantId) {
		LegalEntityPOJO newLegalEntity = new LegalEntityPOJO();
		newLegalEntity.setLegalEntityName(legalEntityCreateDTO.getLegalEntityName());
		newLegalEntity.setTenantId(tenantId);
		return legalEntityRepository.save(newLegalEntity).getId();
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String legalEntityId, String tenantId) {
		LegalEntityPOJO legalEntity = legalEntityRepository.findByIdAndTenantId(legalEntityId, tenantId);
		if(legalEntity == null) {
			return false;
		}
		return true;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(LegalEntityCreateDTO legalEntityCreateDTO, String tenantId) {
		String id = legalEntityRepository.isExist(legalEntityCreateDTO.getLegalEntityName(), tenantId);
		if(id == null) {
			return false;
		}
		return true;
	}
	
	public String updateLegalEntity(String legalEntityId, LegalEntityCreateDTO legalEntityCreateDTO) {
		LegalEntityPOJO legalEntityPOJO = legalEntityRepository.getOne(legalEntityId);
		legalEntityPOJO.setLegalEntityName(legalEntityCreateDTO.getLegalEntityName());
		
		return legalEntityRepository.save(legalEntityPOJO).getId();	
	}

//	public List<LegalEntityPOJO> getAllLegalEntity() {
//		return legalEntityRepository.findAll();
//	}
	
	public List<LegalEntityPOJO> fetchLegalEntityTenantId(String tenantId) {
		return legalEntityRepository.findByTenantId(tenantId);
	}
	
	public LegalEntityPOJO fetchLegalEntityId(String legalEntityId) {
		return legalEntityRepository.findByLegalEntityId(legalEntityId);
	}
}
