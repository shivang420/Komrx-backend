package com.komrz.trackxbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.VendorCreateDTO;
import com.komrz.trackxbackend.dto.VendorFetchDTO;
import com.komrz.trackxbackend.model.VendorPOJO;
import com.komrz.trackxbackend.repository.FetchRepository;
import com.komrz.trackxbackend.repository.VendorRepository;

@Service
public class VendorService {

	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private FetchRepository fetchRepository;
	
	public String createVendor(VendorCreateDTO vendorCreateDTO, String tenantId) {
		VendorPOJO newVendor = new VendorPOJO();
		newVendor.setVendorName(vendorCreateDTO.getVendorName());
		newVendor.setVendorParentId(vendorCreateDTO.getVendorParentId());
		newVendor.setVendorContactName(vendorCreateDTO.getVendorContactName());
		newVendor.setAddress(vendorCreateDTO.getAddress());
		newVendor.setCity(vendorCreateDTO.getCity());
		newVendor.setCountry(vendorCreateDTO.getCountry());
		newVendor.setCountryCode(vendorCreateDTO.getCountryCode());
		newVendor.setPostalCode(vendorCreateDTO.getPostalCode());
		newVendor.setPhone(vendorCreateDTO.getPhone());
		newVendor.setEmail(vendorCreateDTO.getEmail());
		newVendor.setPaymentTerms(vendorCreateDTO.getPaymentTerms());
		newVendor.setVendorLanguage(vendorCreateDTO.getVendorLanguage());
		newVendor.setBankAccNo(vendorCreateDTO.getBankAccNo());
		newVendor.setBankName(vendorCreateDTO.getBankName());
		newVendor.setBankSwiftCode(vendorCreateDTO.getBankSwiftCode());
		newVendor.setCurrency(vendorCreateDTO.getCurrency());
		newVendor.setBankAddress(vendorCreateDTO.getBankAddress());
		newVendor.setTenantId(tenantId);
		newVendor.setStatus(vendorCreateDTO.getStatus());
		newVendor.setVendorType(vendorCreateDTO.getVendorType());
		
		return vendorRepository.save(newVendor).getId();
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String vendorId, String tenantId) {
		List<VendorPOJO> vendor = vendorRepository.findByIdAndTenantId(vendorId, tenantId);
		if(vendor.isEmpty()) {
			return false;
		}
		return true;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(VendorCreateDTO vendorCreateDTO, String tenantId) {
		String check = vendorRepository.isExist(vendorCreateDTO.getVendorName(), tenantId);
		if(check == null) {
			return false;
		}
		return true;
	}

	public String updateVendor(String vendorId, VendorCreateDTO vendorCreateDTO) {
		VendorPOJO vendorPOJO = vendorRepository.getOne(vendorId);
		vendorPOJO.setVendorName(vendorCreateDTO.getVendorName());
		vendorPOJO.setVendorParentId(vendorPOJO.getVendorParentId());
		vendorPOJO.setVendorContactName(vendorCreateDTO.getVendorContactName());
		vendorPOJO.setAddress(vendorCreateDTO.getAddress());
		vendorPOJO.setCity(vendorCreateDTO.getCity());
		vendorPOJO.setCountry(vendorCreateDTO.getCountry());
		vendorPOJO.setCountryCode(vendorCreateDTO.getCountryCode());
		vendorPOJO.setPostalCode(vendorCreateDTO.getPostalCode());
		vendorPOJO.setPhone(vendorCreateDTO.getPhone());
		vendorPOJO.setEmail(vendorCreateDTO.getEmail());
		vendorPOJO.setPaymentTerms(vendorCreateDTO.getPaymentTerms());
		vendorPOJO.setVendorLanguage(vendorCreateDTO.getVendorLanguage());
		vendorPOJO.setBankAccNo(vendorCreateDTO.getBankAccNo());
		vendorPOJO.setBankName(vendorCreateDTO.getBankName());
		vendorPOJO.setBankSwiftCode(vendorCreateDTO.getBankSwiftCode());
		vendorPOJO.setCurrency(vendorCreateDTO.getCurrency());
		vendorPOJO.setBankAddress(vendorCreateDTO.getBankAddress());
		vendorPOJO.setStatus(vendorCreateDTO.getStatus());
		vendorPOJO.setVendorType(vendorCreateDTO.getVendorType());
		
		return vendorRepository.save(vendorPOJO).getId();
	}

//	public List<VendorPOJO> getAllVendor(String tenantId) {
//		return vendorRepository.findByTenantId(tenantId);
//	}
	
	public List<VendorFetchDTO> getAllVendorsDTO(String tenantId) {
		return fetchRepository.getVendor(tenantId);
	}

	public VendorPOJO fetchVendorId(String vendorId) {
		return vendorRepository.findByVendorId(vendorId);
	}
}
