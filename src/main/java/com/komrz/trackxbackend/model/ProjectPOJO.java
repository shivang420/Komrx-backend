package com.komrz.trackxbackend.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.PppStatus;

@Entity
@Table(name = "project", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private String id;
	
	@Column(name = "project_name")
	private String projectName;
	
//	@Column(name = "program_id")
//	private String programId;
	
	@ManyToOne
	@JoinColumn(name = "program_id", referencedColumnName = "program_id")
	@JsonIgnoreProperties("projects")
	private ProgramPOJO program;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private PppStatus status;
	
	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public ProgramPOJO getProgram() {
		return program;
	}

	public void setProgram(ProgramPOJO program) {
		this.program = program;
	}	

	public PppStatus getStatus() {
		return status;
	}

	public void setStatus(PppStatus status) {
		this.status = status;
	}
//
//	public Set<ContractPOJO> getContracts() {
//		return contracts;
//	}
//
//	public void setContracts(Set<ContractPOJO> contracts) {
//		this.contracts = contracts;
//	}
}
