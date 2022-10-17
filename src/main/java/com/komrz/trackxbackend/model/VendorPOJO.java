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
import com.komrz.trackxbackend.enumerator.VendorStatusEn;
import com.komrz.trackxbackend.enumerator.VendorTypeEn;

@Entity
@Table(name = "vendor", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VendorPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendor_id")
	private String id;
	
	@Column(name = "vendor_name")
	private String vendorName;
	
	@Column(name = "vendor_parent")
	private String vendorParentId;
	
	@Column(name = "vendor_contact_name")
	private String vendorContactName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "country_code")
	private String countryCode;
	
	@Column(name = "postal_code")
	private int postalCode;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "payment_terms")
	private String paymentTerms;
	
	@Column(name = "vendor_language")
	private String vendorLanguage;
	
	@Column(name = "bank_acc_no")
	private String bankAccNo;
	
	@Column(name = "bank_name")
	private String bankName;
	
	@Column(name = "bank_swift_code")
	private String bankSwiftCode;
	
	@Column(name = "currency")
	private String currency;
	
	@Column(name = "bank_address")
	private String bankAddress;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private VendorStatusEn status;
	
	@Column(name = "vendor_type")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private VendorTypeEn vendorType;
	
//	@OneToMany
//	@JoinColumn(name = "vendor_id")
//	@JsonIgnoreProperties({"vendor","project"})
//	private Set<ContractPOJO> vendorContracts;

	
	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorParentId() {
		return vendorParentId;
	}

	public void setVendorParentId(String vendorParentId) {
		this.vendorParentId = vendorParentId;
	}

	public String getVendorContactName() {
		return vendorContactName;
	}

	public void setVendorContactName(String vendorContactName) {
		this.vendorContactName = vendorContactName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getVendorLanguage() {
		return vendorLanguage;
	}

	public void setVendorLanguage(String vendorLanguage) {
		this.vendorLanguage = vendorLanguage;
	}

	public String getBankAccNo() {
		return bankAccNo;
	}

	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankSwiftCode() {
		return bankSwiftCode;
	}

	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public VendorStatusEn getStatus() {
		return status;
	}

	public void setStatus(VendorStatusEn status) {
		this.status = status;
	}

	public VendorTypeEn getVendorType() {
		return vendorType;
	}

	public void setVendorType(VendorTypeEn vendorType) {
		this.vendorType = vendorType;
	}
}
