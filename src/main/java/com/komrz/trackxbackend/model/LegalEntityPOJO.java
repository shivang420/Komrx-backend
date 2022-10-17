package com.komrz.trackxbackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "legal_entity", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LegalEntityPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "legal_entity_id")
	private String id;
	
	@Column(name = "legal_entity_name")
	private String legalEntityName;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@OneToMany(mappedBy = "legalEntity")
	@JsonIgnoreProperties("legalEntity")
    private Set<CostCenterPOJO> costCenters;

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLegalEntityName() {
		return legalEntityName;
	}

	public void setLegalEntityName(String legalEntityName) {
		this.legalEntityName = legalEntityName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Set<CostCenterPOJO> getCostCenters() {
		return costCenters;
	}

	public void setCostCenters(Set<CostCenterPOJO> costCenters) {
		this.costCenters = costCenters;
	}
}
