package com.komrz.trackxbackend.dto;

import java.util.List;

public class CategoryProcurementViewDTO {

	private String contractCategoryName;
	private List<Double> amount;
	
	public String getContractCategoryName() {
		return contractCategoryName;
	}
	public void setContractCategoryName(String contractCategoryName) {
		this.contractCategoryName = contractCategoryName;
	}
	public List<Double> getAmount() {
		return amount;
	}
	public void setAmount(List<Double> amount) {
		this.amount = amount;
	}
}
