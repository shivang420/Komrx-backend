package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

import com.komrz.trackxbackend.enumerator.VendorStatusEn;
import com.komrz.trackxbackend.enumerator.VendorTypeEn;

public class VendorCreateDTO {

	@NotBlank(message = "Vendor Name cannot be blank!")
	private String vendorName;
	private String vendorParentId;
	private String vendorContactName;
	private String address;
	private String city;
	private String country;
	private String countryCode;
	private int postalCode;
	private String phone;
	private String email;
	private String paymentTerms;
	private String vendorLanguage;
	private String bankAccNo;
	private String bankName;
	private String bankSwiftCode;
	private String currency;
	private String bankAddress;
	private VendorStatusEn status;
	private VendorTypeEn vendorType;
	
	//getters and setters
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorParentId() {
		return vendorParentId;
	}
	public void setVendorParentId(String vendorParentId) {
		this.vendorParentId = vendorParentId;
	}
	public String getVendorContactName() {
		return vendorContactName;
	}
	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getVendorLanguage() {
		return vendorLanguage;
	}
	public void setVendorLanguage(String vendorLanguage) {
		this.vendorLanguage = vendorLanguage;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankSwiftCode() {
		return bankSwiftCode;
	}
	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getBankAddress() {
		return bankAddress;
	}
	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}
	public VendorStatusEn getStatus() {
		return status;
	}
	public void setStatus(VendorStatusEn status) {
		this.status = status;
	}	
	public VendorTypeEn getVendorType() {
		return vendorType;
	}
	public void setVendorType(VendorTypeEn vendorType) {
		this.vendorType = vendorType;
	}
	@Override
	public String toString() {
		return "vendorCreateDTO{ " + 
				"vendorName: " + vendorName +
				", vendorContactName: " + vendorContactName + 
				", vendorCountry: " + country + 
				", vendorLanguage: " + vendorLanguage + 
				"}";
	}
}
