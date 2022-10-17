package com.komrz.trackxbackend.dto;

public class VendorTypeProcurementViewDTO {

	private String vendorType;
	private int count = 0;
	
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
