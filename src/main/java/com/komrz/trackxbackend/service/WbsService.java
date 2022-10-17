package com.komrz.trackxbackend.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.WbsCreateDTO;
import com.komrz.trackxbackend.model.WbsPOJO;
import com.komrz.trackxbackend.repository.CostCenterRepository;
import com.komrz.trackxbackend.repository.WbsRepository;

@Service
public class WbsService {

	@Autowired
	private WbsRepository wbsRepository;
	
	@Autowired
	private CostCenterRepository costCenterRepository;
	
	public String createWbs(WbsCreateDTO wbsCreateDTO) {
		WbsPOJO newWbs = new WbsPOJO();
		newWbs.setWbsName(wbsCreateDTO.getWbsName());
		newWbs.setCostCenter(costCenterRepository.getOne(wbsCreateDTO.getCostCenterId()));
		return wbsRepository.save(newWbs).getId();	
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String wbsId) {
		return wbsRepository.existsById(wbsId);
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(WbsCreateDTO wbsCreateDTO, String tenantId) {
		List<WbsPOJO> wbs= wbsRepository.findByWbsName(wbsCreateDTO.getWbsName());
		if(wbs.isEmpty()) {
			return false;
		}
		ListIterator<WbsPOJO> listIterator = wbs.listIterator();
		while (listIterator.hasNext()) {
			if(tenantId.equals(listIterator.next().getCostCenter().getLegalEntity().getTenantId())) {
				return true;
			}	
		}
		return false;
	}
	
	public String updateWbs(String wbsId, WbsCreateDTO wbsCreateDTO) {
		
		WbsPOJO wbsPOJO = wbsRepository.getOne(wbsId);
		wbsPOJO.setWbsName(wbsCreateDTO.getWbsName());
		return wbsRepository.save(wbsPOJO).getId();		
	}

	public List<WbsPOJO> getAllWbs(String costCenterId) {
		return wbsRepository.findByCostCenterId(costCenterId);
	}
	
	public WbsPOJO fetchWbsId(String wbsId) {	
		return wbsRepository.findByWbsId(wbsId);
	}

	public boolean isExist(String wbsId, String tenantId) {
		WbsPOJO wbs = wbsRepository.findByWbsId(wbsId);
		if(wbs == null) {
			return false;
		}
		if(tenantId.equals(wbs.getCostCenter().getLegalEntity().getTenantId())) {
			return true;
		}
		return false;
	}
}
