package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ContractCategoryCreateDTO;
import com.komrz.trackxbackend.model.ContractCategoryPOJO;
import com.komrz.trackxbackend.repository.ContractCategoryRepository;

@Service
public class ContractCategoryService {

	@Autowired
	private ContractCategoryRepository contractCategoryRepository;
	
	public String createContractCategory(ContractCategoryCreateDTO contractCategoryCreateDTO, String tenantId) {
		ContractCategoryPOJO newContractCategory = new ContractCategoryPOJO();
		newContractCategory.setContractCategoryName(contractCategoryCreateDTO.getContractCategoryName());
		newContractCategory.setTenantId(tenantId);
		return contractCategoryRepository.save(newContractCategory).getId();
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String contractCategoryId, String tenantId) {
		ContractCategoryPOJO contractCategoryPOJO = contractCategoryRepository.findByIdAndTenantId(contractCategoryId, tenantId);
		if(contractCategoryPOJO == null) {
			return false;
		}
		return true;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(ContractCategoryCreateDTO contractCategoryCreateDTO, String tenantId) {
		String check = contractCategoryRepository.isExist(contractCategoryCreateDTO.getContractCategoryName(), tenantId);
		if(check == null) {
			return false;
		}
		return true;
	}
	
	public String updateContractCategory(String contractCategoryId, ContractCategoryCreateDTO contractCategoryCreateDTO) {
		ContractCategoryPOJO contractCategoryPOJO = contractCategoryRepository.getOne(contractCategoryId);
		contractCategoryPOJO.setContractCategoryName(contractCategoryCreateDTO.getContractCategoryName());
		return contractCategoryRepository.save(contractCategoryPOJO).getId();
	}

	public ContractCategoryPOJO fetchContractCategoryId(String contractCategoryId) {
		return contractCategoryRepository.findByContractCategoryId(contractCategoryId);
	}
	
	public List<ContractCategoryPOJO> fetchContractCategoryTenantId(String tenantId) {
		return contractCategoryRepository.findByTenantId(tenantId);	
	}
}
