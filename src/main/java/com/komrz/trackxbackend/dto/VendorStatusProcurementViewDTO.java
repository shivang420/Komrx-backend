package com.komrz.trackxbackend.dto;

public class VendorStatusProcurementViewDTO {

	private String vendorStatus;
	private int count = 0;
	
	public String getVendorStatus() {
		return vendorStatus;
	}
	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
