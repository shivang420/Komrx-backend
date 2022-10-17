package com.komrz.trackxbackend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.EventTypeEn;

@Entity
@Table(name = "event", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private String id;
	
	@Column(name = "event_title")
	private String eventTitle;
	
	@Column(name = "event_description")
	private String eventDescription;
	
	@Column(name = "start_time")
	private Date startDate;
	
	@Column(name = "end_time")
	private Date endDate;
	
	@Column(name = "is_full_day_event")
	private boolean isFullDayEvent;
	
	@Column(name = "is_recurring")
	private boolean isRecurring;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "contract_id")
	private String contractId;
	
	@Column(name = "parent_event_id")
	private String parentEventId;
	
	@Column(name = "is_private")
	private boolean isPrivate;
	
	@Column(name = "conference_link")
	private String conferenceLink;
	
	@Column(name = "event_type")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private EventTypeEn eventTypeEn;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "no_of_files")
	private int count;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "event_user_invite", schema = "trackx",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
            )
    private Set<User> eventUsers = new HashSet<>();
	
	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDescription() {
		return eventDescription;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setFullDayEvent(boolean isFullDayEvent) {
		this.isFullDayEvent = isFullDayEvent;
	}

	public void setRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Set<User> getEventUsers() {
		return eventUsers;
	}

	public void setEventUsers(Set<User> eventUsers) {
		this.eventUsers = eventUsers;
	}
}
