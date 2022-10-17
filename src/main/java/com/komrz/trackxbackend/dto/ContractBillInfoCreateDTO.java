package com.komrz.trackxbackend.dto;

import java.sql.Date;

import com.komrz.trackxbackend.enumerator.BillPaymentTypeEn;

public class ContractBillInfoCreateDTO {

	private Date billDate;
	private String currency;
	
	private int billAmount;
	private BillPaymentTypeEn paymentType;
	private String note;
	
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
}
