package com.komrz.trackxbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "wbs", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WbsPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "wbs_id")
	private String id;
	
	@Column(name = "wbs_name")
	private String wbsName;
	
//	@Column(name = "cost_center_id")
//	private String costCenterId;
	
	@ManyToOne
	@JoinColumn(name = "cost_center_id", referencedColumnName = "cost_center_id")
	@JsonIgnoreProperties("wbss")
	private CostCenterPOJO costCenter;
	
//	@OneToMany
//	@JoinColumn(name = "wbs_id")
//	@JsonIgnoreProperties({"wbs", "project"})
//	private Set<ContractPOJO> wbsContracts;

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWbsName() {
		return wbsName;
	}

	public void setWbsName(String wbsName) {
		this.wbsName = wbsName;
	}

	public CostCenterPOJO getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(CostCenterPOJO costCenter) {
		this.costCenter = costCenter;
	}
}
