package com.komrz.trackxbackend.dto;

import java.util.ArrayList;
import java.util.List;

public class CompleteVendorStatusProcurementViewDTO {

	private List<VendorStatusProcurementViewDTO> vendorStatus = new ArrayList<>();
	private List<VendorTypeProcurementViewDTO> vendorType = new ArrayList<>();
	
	public List<VendorStatusProcurementViewDTO> getVendorStatus() {
		return vendorStatus;
	}
	public void setVendorStatus(List<VendorStatusProcurementViewDTO> vendorStatus) {
		this.vendorStatus = vendorStatus;
	}
	public List<VendorTypeProcurementViewDTO> getVendorType() {
		return vendorType;
	}
	public void setVendorType(List<VendorTypeProcurementViewDTO> vendorType) {
		this.vendorType = vendorType;
	}
}
