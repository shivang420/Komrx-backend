package com.komrz.trackxbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "curr_exchange_rate", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CurrExchangeRate {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;

	@Column(name = "from_curr_code")
	private String fromCurrCode;
	
	@Column(name = "to_curr_code")
	private String toCurrCode;
	
	@Column(name = "conversion_value")
	private float conversionValue;
	
	@Column(name = "inverse_conversion_value")
	private float inverseConversionValue;

	//Getters and setters
	public String getFromCurrCode() {
		return fromCurrCode;
	}

	public void setFromCurrCode(String fromCurrCode) {
		this.fromCurrCode = fromCurrCode;
	}

	public String getToCurrCode() {
		return toCurrCode;
	}

	public void setToCurrCode(String toCurrCode) {
		this.toCurrCode = toCurrCode;
	}

	public float getConversionValue() {
		return conversionValue;
	}

	public void setConversionValue(float conversionValue) {
		this.conversionValue = conversionValue;
	}

	public float getInverseConversionValue() {
		return inverseConversionValue;
	}

	public void setInverseConversionValue(float inverseConversionValue) {
		this.inverseConversionValue = inverseConversionValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
