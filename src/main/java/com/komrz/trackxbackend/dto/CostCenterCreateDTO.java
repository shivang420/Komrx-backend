package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class CostCenterCreateDTO {
	
	@NotBlank(message = "Cost Center Name cannot be blank!")
	private String costCenterName;
	
	@NotBlank(message = "Legal Entity Id cannot be blank!")
	private String legalEntityId;
	
	public String getCostCenterName() {
		return costCenterName;
	}
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}
	public String getLegalEntityId() {
		return legalEntityId;
	}
	public void setLegalEntityId(String legalEntityId) {
		this.legalEntityId = legalEntityId;
	}
}
