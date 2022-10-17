package com.komrz.trackxbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.IndustryTypeEn;

@Entity
@Table(name = "tenant_details", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TenantDetailsPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tenant_id")
	private String id;
	
	@Column(name = "tenant_name")
	private String tenantName;
	
	@Column(name = "status")
	private char status;
	
	@Column(name = "industry_type")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private IndustryTypeEn industryType;
	
	@Column(name = "pref_currency")
	private String prefCurrency;

	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public IndustryTypeEn getIndustryType() {
		return industryType;
	}

	public void setIndustryType(IndustryTypeEn industryType) {
		this.industryType = industryType;
	}

	public String getPrefCurrency() {
		return prefCurrency;
	}

	public void setPrefCurrency(String prefCurrency) {
		this.prefCurrency = prefCurrency;
	}
}
