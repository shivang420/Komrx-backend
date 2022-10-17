package com.komrz.trackxbackend.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.CostCenterCreateDTO;
import com.komrz.trackxbackend.model.CostCenterPOJO;
import com.komrz.trackxbackend.repository.CostCenterRepository;
import com.komrz.trackxbackend.repository.LegalEntityRepository;

@Service
public class CostCenterService {

	@Autowired
	private CostCenterRepository costCenterRepository;
	
	@Autowired
	private LegalEntityRepository legalEntityRepository;
	
	public String createCostCenter(CostCenterCreateDTO costCenterCreateDTO) {
		CostCenterPOJO newCostCenter = new CostCenterPOJO();
		newCostCenter.setCostCenterName(costCenterCreateDTO.getCostCenterName());
		newCostCenter.setLegalEntity(legalEntityRepository.getOne(costCenterCreateDTO.getLegalEntityId()));
		return costCenterRepository.save(newCostCenter).getId();	
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String costCenterId) {
		return costCenterRepository.existsById(costCenterId);
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(CostCenterCreateDTO costCenterCreateDTO, String tenantid) {
		List<CostCenterPOJO> costCenter = costCenterRepository.findByCostCenterName(costCenterCreateDTO.getCostCenterName());
		if (costCenter.isEmpty()) {
			return false;
		}
		ListIterator<CostCenterPOJO> listIterator = costCenter.listIterator();
		while (listIterator.hasNext()) {
			if(tenantid.equals(listIterator.next().getLegalEntity().getTenantId())) {
				return true;
			}
		}
		return false;
	}
	
	public String updateCostCenter(String costCenterId, CostCenterCreateDTO costCenterCreateDTO) {
		
		CostCenterPOJO costCenterPOJO = costCenterRepository.getOne(costCenterId);
		costCenterPOJO.setCostCenterName(costCenterCreateDTO.getCostCenterName());
		return costCenterRepository.save(costCenterPOJO).getId();		
	}

//	public List<CostCenterPOJO> getAllCostCenter() {
//		return costCenterRepository.findAll();
//	}
	
	public List<CostCenterPOJO> getAllCostCenter(String legalEntityId) {
		return costCenterRepository.findByLegalEntityId(legalEntityId);
	}
	
	public CostCenterPOJO fetchCostCenterId(String costCenterId) {	
		return costCenterRepository.findByCostCenterId(costCenterId);
	}

	public boolean isExist(String costCenterId, String tenantId) {
		CostCenterPOJO costCenter = costCenterRepository.findByCostCenterId(costCenterId);
		if(costCenter == null) {
			return false;
		}
		if(tenantId.equals(costCenter.getLegalEntity().getTenantId())) {
			return true;
		}
		return false;
	}
}
