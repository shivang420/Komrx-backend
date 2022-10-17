package com.komrz.trackxbackend.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.komrz.trackxbackend.enumerator.EventTypeEn;

public class EventCreateDTO {

	@NotNull(message = "Event Title cannot be blank!")
	@Size(message = "Event Title cannot be blank", min = 3, max=30)
	private String eventTitle;	
	private String eventDescription;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	private boolean isFullDayEvent;
	private boolean isRecurring;
	
	@NotNull(message = "Contract Id cannot be blank!")
	private String contractId;
	private String parentEventId;
	private boolean isPrivate;
	private String conferenceLink;
	private EventTypeEn eventTypeEn;
	private String location;
	private Set<String> eventUsers = new HashSet<String>();
	
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean getIsFullDayEvent() {
		return isFullDayEvent;
	}
	public void setIsFullDayEvent(boolean isFullDayEvent) {
		this.isFullDayEvent = isFullDayEvent;
	}
	public boolean getIsRecurring() {
		return isRecurring;
	}
	public void setIsRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getParentEventId() {
		return parentEventId;
	}
	public void setParentEventId(String parentEventId) {
		this.parentEventId = parentEventId;
	}
	public boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public String getConferenceLink() {
		return conferenceLink;
	}
	public void setConferenceLink(String conferenceLink) {
		this.conferenceLink = conferenceLink;
	}
	public EventTypeEn getEventTypeEn() {
		return eventTypeEn;
	}
	public void setEventTypeEn(EventTypeEn eventTypeEn) {
		this.eventTypeEn = eventTypeEn;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Set<String> getEventUsers() {
		return eventUsers;
	}
	public void setEventUsers(Set<String> eventUsers) {
		this.eventUsers = eventUsers;
	}
}
