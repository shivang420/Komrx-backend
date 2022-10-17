package com.komrz.trackxbackend.dto;

import javax.validation.constraints.NotBlank;

public class ProjectCreateDTO {

	@NotBlank(message = "Project Name cannot be blank!")
	private String projectName;
	
	@NotBlank(message = "Program Id cannot be blank!")
	private String programId;

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
}
