package com.komrz.trackxbackend.model;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.PppStatus;

@Entity
@Table(name = "program", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProgramPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "program_id")
	private String id;
	
	@Column(name = "program_name")
	private String programName;
	
//	@Column(name = "portfolio_id")
//	private String portfolioId;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id", referencedColumnName = "portfolio_id")
	@JsonIgnoreProperties("programs")
	private PortfolioPOJO portfolio;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private PppStatus status;
	
	@OneToMany
	@JoinColumn(name = "program_id")
	@JsonIgnoreProperties("program")
    private Set<ProjectPOJO> projects ;

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public PppStatus getStatus() {
		return status;
	}

	public void setStatus(PppStatus status) {
		this.status = status;
	}

	public Set<ProjectPOJO> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectPOJO> projects) {
		this.projects = projects;
	}

	public PortfolioPOJO getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(PortfolioPOJO portfolio) {
		this.portfolio = portfolio;
	}
}
