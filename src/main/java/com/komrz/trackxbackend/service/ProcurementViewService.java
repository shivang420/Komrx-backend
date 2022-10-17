package com.komrz.trackxbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ActiveContractEventProcurementView;
import com.komrz.trackxbackend.dto.CategoryProcurementViewDTO;
import com.komrz.trackxbackend.dto.CompleteVendorStatusProcurementViewDTO;
import com.komrz.trackxbackend.dto.ContractExpiryProcurementViewDTO;
import com.komrz.trackxbackend.dto.TopVendorProcurementViewDTO;
import com.komrz.trackxbackend.dto.VendorFetchDTO;
import com.komrz.trackxbackend.dto.VendorWiseCategoryProcurementViewDTO;
import com.komrz.trackxbackend.dto.YoyComparisonProcurementViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.model.ContractCategoryPOJO;
import com.komrz.trackxbackend.repository.ProcurementViewRepository;

@Service
public class ProcurementViewService {

	@Autowired
	private ProcurementViewRepository procurementViewRepository;
	
	@Autowired
	private ContractCategoryService contractCategoryService;
	
	public CompleteVendorStatusProcurementViewDTO vendorStatusProcurementView(String tenantId, BuySellEn buySell) {
		CompleteVendorStatusProcurementViewDTO vendorStatus = new CompleteVendorStatusProcurementViewDTO();
		vendorStatus.setVendorStatus(procurementViewRepository.vendorStatus(tenantId, buySell));
		vendorStatus.setVendorType(procurementViewRepository.vendorType(tenantId, buySell));
		return vendorStatus;
	}
	
	public List<YoyComparisonProcurementViewDTO> yoyComparisonProcurementView(String tenantId, String year){
		return procurementViewRepository.yoyComparison(tenantId, year);
	}
	
	public List<TopVendorProcurementViewDTO> topVendorProcurementView(String tenantId, BuySellEn buySell, String year) {
		return procurementViewRepository.topVendor(tenantId, buySell, year);
	}
	
	public List<ContractExpiryProcurementViewDTO> contractExpiryProcurementView(String tenantId, int startPeriod, int endPeriod){
		return procurementViewRepository.contractExpiry(tenantId, startPeriod, endPeriod);
	}
	
	public List<ActiveContractEventProcurementView> activeContractEventProcurementView(String tenantId){
		return procurementViewRepository.activeContractEvent(tenantId);
	}
	
	public VendorWiseCategoryProcurementViewDTO vendorWiseCategoryProcurementView(String tenantId, BuySellEn buySell, String year){
//		return procurementViewRepository.vendorWiseCategory(tenantId, buySell, year);
		List<VendorFetchDTO> vendorProcurementViewDTOs = procurementViewRepository.topVendorForCategory(tenantId, buySell, year);
		List<String> vendorName = new ArrayList<>();
		vendorProcurementViewDTOs.forEach((ch) -> {
			vendorName.add(ch.getVendorName());
		});
		List<ContractCategoryPOJO> contractCategory = contractCategoryService.fetchContractCategoryTenantId(tenantId);
		
		VendorWiseCategoryProcurementViewDTO response = new VendorWiseCategoryProcurementViewDTO();
		response.setVendors(vendorName);
		List<CategoryProcurementViewDTO> category = new ArrayList<>();
		
		ListIterator<ContractCategoryPOJO> listIterator = contractCategory.listIterator();
		while (listIterator.hasNext()) {
			category.add(getAmount(tenantId, buySell, year, listIterator.next(), vendorProcurementViewDTOs));
		}
		response.setContractCategory(category);
		return response;
	}
	
	private CategoryProcurementViewDTO getAmount(String tenantId, BuySellEn buySell, String year, ContractCategoryPOJO category, List<VendorFetchDTO> vendor ) {
		CategoryProcurementViewDTO response = new CategoryProcurementViewDTO();
		response.setContractCategoryName(category.getContractCategoryName());
		List<Double> amount = new ArrayList<>();
		ListIterator<VendorFetchDTO> listIterator = vendor.listIterator();
		while (listIterator.hasNext()) {
			amount.add(procurementViewRepository.vendorWiseCategory(tenantId, buySell, year, listIterator.next().getVendorId(), category.getId()));
		}
		response.setAmount(amount);
		return response;
	}
}
