package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class ContractSubCategoryCreateDTO {

	@NotBlank(message = "Contract Sub Category Name cannot be blank!")
	private String contractSubCategoryName;
	
	@NotBlank(message = "Contract Category Id cannot be blank!")
	private String contractCategoryId;
	
	public String getContractSubCategoryName() {
		return contractSubCategoryName;
	}
	public void setContractSubCategoryName(String contractSubCategoryName) {
		this.contractSubCategoryName = contractSubCategoryName;
	}
	public String getContractCategoryId() {
		return contractCategoryId;
	}
	public void setContractCategoryId(String contractCategoryId) {
		this.contractCategoryId = contractCategoryId;
	}
}
