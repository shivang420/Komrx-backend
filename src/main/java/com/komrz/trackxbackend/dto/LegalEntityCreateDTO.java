package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class LegalEntityCreateDTO {

	@NotBlank(message = "Legal Entity Name cannot be blank!")
	private String legalEntityName;
	
	public String getLegalEntityName() {
		return legalEntityName;
	}
	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}
}
