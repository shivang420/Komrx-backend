package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.AllContractViewDTO;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;
import com.komrz.trackxbackend.repository.ContractViewRepository;

@Service
public class ContractViewService {

	@Autowired
	private ContractViewRepository contractViewRepository;
	
	public List<AllContractViewDTO> allContractView(String tenantId, ContractStatusEn contractStatus) {
		return contractViewRepository.allContractView(tenantId, contractStatus);
	}
}
