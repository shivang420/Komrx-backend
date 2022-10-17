package com.komrz.trackxbackend.dto;

import java.util.ArrayList;
import java.util.List;

public class VendorWiseCategoryProcurementViewDTO {

	private List<String> vendors = new ArrayList<>();
	private List<CategoryProcurementViewDTO> contractCategory = new ArrayList<>();
	
	public List<String> getVendors() {
		return vendors;
	}
	public void setVendors(List<String> vendors) {
		this.vendors = vendors;
	}
	public List<CategoryProcurementViewDTO> getContractCategory() {
		return contractCategory;
	}
	public void setContractCategory(List<CategoryProcurementViewDTO> contractCategory) {
		this.contractCategory = contractCategory;
	}
}
