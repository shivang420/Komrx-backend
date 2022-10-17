package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class WbsCreateDTO {

	@NotBlank(message = "Wbs Name cannot be blank!")
	private String wbsName;
	
	@NotBlank(message = "Cost Center Id cannot be blank!")
	private String costCenterId;
	
	public String getWbsName() {
		return wbsName;
	}
	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}
	public String getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}
}
