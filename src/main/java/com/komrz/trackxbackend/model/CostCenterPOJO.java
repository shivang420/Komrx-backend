package com.komrz.trackxbackend.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "cost_center", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CostCenterPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cost_center_id")
	private String id;
	
	@Column(name = "cost_center_name")
	private String costCenterName;
	
//	@Column(name = "legal_entity_id")
//	private String legalEntityId;
	
	@ManyToOne
	@JoinColumn(name = "legal_entity_id", referencedColumnName = "legal_entity_id")
	@JsonIgnoreProperties("costCenters")
	private LegalEntityPOJO legalEntity;
	
	@OneToMany
	@JoinColumn(name = "cost_center_id")
	@JsonIgnoreProperties("costCenter")
	private Set<WbsPOJO> wbss;
	
//	@OneToMany
//	@JoinColumn(name = "cost_center_id")
//	@JsonIgnoreProperties({"costCenter", "wbsContracts", "project", "vendor", "wbs"})
//	private Set<ContractPOJO> costCenterContracts;

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCostCenterName() {
		return costCenterName;
	}

	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

//	public String getLegalEntityId() {
//		return legalEntityId;
//	}
//
//	public void setLegalEntityId(String legalEntityId) {
//		this.legalEntityId = legalEntityId;
//	}

	public Set<WbsPOJO> getWbss() {
		return wbss;
	}

	public void setWbss(Set<WbsPOJO> wbss) {
		this.wbss = wbss;
	}

	public LegalEntityPOJO getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntityPOJO legalEntity) {
		this.legalEntity = legalEntity;
	}	
}
