package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class ContractCategoryCreateDTO {

	@NotBlank(message = "Contract Category Name cannot be blank!")
	private String contractCategoryName;
	
	public String getContractCategoryName() {
		return contractCategoryName;
	}
	public void setContractCategoryName(String contractCategoryName) {
		this.contractCategoryName = contractCategoryName;
	}
}
