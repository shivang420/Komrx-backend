package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class UserCreateDTO {

	@NotBlank(message = "Name cannot be blank!")
	private String name;
	
	@NotBlank(message = "Password cannot be blank!")
    private String password;	
	private String countryCode;
	private String contactNo;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
