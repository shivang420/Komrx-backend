package com.komrz.trackxbackend.model;

import java.sql.Date;

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
import com.komrz.trackxbackend.enumerator.BillPaymentTypeEn;

@Entity
@Table(name = "contract_bill_info", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContractBillInfoPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_bill_id")
	private String id;
	
	@Column(name = "contract_id")
	private String contractId;

	@Column(name = "bill_date")
	private Date billDate;

	@Column(name = "currency")
	private String currency;

	@Column(name = "bill_amount")
	private int billAmount;

	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	@Column(name = "payment_type")
	private BillPaymentTypeEn paymentType;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "bill_amnt_pref_curr")
	private double billAmntPrefCurr;
	
	//getters and setters
	public String getId() {
		return id;
	}

	public String getContractId() {
		return contractId;
	}

	public Date getBillDate() {
		return billDate;
	}

	public String getCurrency() {
		return currency;
	}

	public int getBillAmount() {
		return billAmount;
	}

	public BillPaymentTypeEn getPaymentType() {
		return paymentType;
	}

	public String getNote() {
		return note;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setBillAmount(int billAmount) {
		this.billAmount = billAmount;
	}

	public void setPaymentType(BillPaymentTypeEn paymentType) {
		this.paymentType = paymentType;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getBillAmntPrefCurr() {
		return billAmntPrefCurr;
	}

	public void setBillAmntPrefCurr(double billAmntPrefCurr) {
		this.billAmntPrefCurr = billAmntPrefCurr;
	}
}
