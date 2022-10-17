package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ContractSubCategoryCreateDTO;
import com.komrz.trackxbackend.model.ContractSubCategoryPOJO;
import com.komrz.trackxbackend.repository.ContractSubCategoryRepository;

@Service
public class ContractSubCategoryService {

	@Autowired
	private ContractSubCategoryRepository contractSubCategoryRepository;
	
	public String createContractSubCategory(ContractSubCategoryCreateDTO contractSubCategoryCreateDTO, String tenantId) {
		ContractSubCategoryPOJO newContractSubCategory = new ContractSubCategoryPOJO();
		newContractSubCategory.setContractSubCategoryName(contractSubCategoryCreateDTO.getContractSubCategoryName());
		newContractSubCategory.setContractCategoryId(contractSubCategoryCreateDTO.getContractCategoryId());
		newContractSubCategory.setTenantId(tenantId);
		return contractSubCategoryRepository.save(newContractSubCategory).getId();	
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String contractSubCategoryId) {
		return contractSubCategoryRepository.existsById(contractSubCategoryId);
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(ContractSubCategoryCreateDTO contractSubCategoryCreateDTO, String contractCategoryId) {
		String id = contractSubCategoryRepository.isExist(contractSubCategoryCreateDTO.getContractSubCategoryName(), contractCategoryId);
		if(id == null) {
			return false;
		}
		return true;
	}
	
	public String updateContractSubCategory(String contractSubCategoryId, ContractSubCategoryCreateDTO contractSubCategoryCreateDTO) {
		ContractSubCategoryPOJO contractSubCategoryPOJO = contractSubCategoryRepository.getOne(contractSubCategoryId);
		contractSubCategoryPOJO.setContractSubCategoryName(contractSubCategoryCreateDTO.getContractSubCategoryName());
		return contractSubCategoryRepository.save(contractSubCategoryPOJO).getId();		
	}
	
	public List<ContractSubCategoryPOJO> getAllContractSubCategory(String contractCategoryId) {
		return contractSubCategoryRepository.findByContractCategoryId(contractCategoryId);
	}
	
	public ContractSubCategoryPOJO fetchContractSubCategoryId(String contractSubCategoryId) {	
		return contractSubCategoryRepository.findByContractSubCategoryId(contractSubCategoryId);
	}

	public boolean isExist(String contractSubCategoryId, String tenantId) {
		ContractSubCategoryPOJO subCategory = contractSubCategoryRepository.findByContractSubCategoryId(contractSubCategoryId);
		if(subCategory == null) {
			return false;
		}
		if(tenantId.equals(subCategory.getTenantId())) {
			return true;
		}
		return false;
	}
}
