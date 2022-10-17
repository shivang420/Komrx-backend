
package com.komrz.trackxbackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.PppStatus;

@Entity
@Table(name = "portfolio", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PortfolioPOJO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "portfolio_id")
	private String id;
	
	@Column(name = "portfolio_name")
	private String name;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private PppStatus status;
	
	@Column(name = "tenant_id")
	private String tenantId;

	@OneToMany(mappedBy = "portfolio")
	@JsonIgnoreProperties("portfolio")
    private Set<ProgramPOJO> programs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PppStatus getStatus() {
		return status;
	}

	public void setStatus(PppStatus status) {
		this.status = status;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Set<ProgramPOJO> getPrograms() {
		return programs;
	}

	public void setProgramPOJOs(Set<ProgramPOJO> programs) {
		this.programs = programs;
	}
}
