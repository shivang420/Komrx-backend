package com.komrz.trackxbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.komrz.trackxbackend.enumerator.IndustryTypeEn;

public class TenantDetailsCreateDTO {

	@NotBlank(message = "Tenant Name Cannot be Blank")
	private String tenantName;
	
	@NotNull(message = "Industry Type Cannot be Blank")
	private IndustryTypeEn industryType;
	
	@NotBlank(message = "Pref Currency Cannot be Blank")
	private String prefCurrency;
	
	@NotBlank(message = "Name Cannot be Blank")
	private String name;
	
	@NotBlank(message = "Username Cannot be Blank")
	@Email(message = "Please enter a valid email")
	private String username;
	
	@NotBlank(message = "Password Cannot be Blank")
    private String password;	
	
	private String countryCode;
	private String contactNo;
	
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public IndustryTypeEn getIndustryType() {
		return industryType;
	}
	public void setIndustryType(IndustryTypeEn industryType) {
		this.industryType = industryType;
	}
	public String getPrefCurrency() {
		return prefCurrency;
	}
	public void setPrefCurrency(String prefCurrency) {
		this.prefCurrency = prefCurrency;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
}
