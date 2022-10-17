package com.komrz.trackxbackend.dto;

import java.util.Date;

import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.enumerator.EventTypeEn;

public class EventFetchDTO {

	private String eventId;
	private String eventName;
	private EventTypeEn eventType;
	private String contractName;
	private Date endDate;
	private String amount;
	private BuySellEn buySell;
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public EventTypeEn getEventType() {
		return eventType;
	}
	public void setEventType(EventTypeEn eventType) {
		this.eventType = eventType;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public BuySellEn getBuySell() {
		return buySell;
	}
	public void setBuySell(BuySellEn buySell) {
		this.buySell = buySell;
	}
}
