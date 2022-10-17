package com.komrz.trackxbackend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "user_invite", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserInvitePOJO {

	@Id
	@Column(name = "user_invite_id")
	private String userInviteId;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "invited_by")
	private String invitedBy;
	
	@Column(name = "invite_date")
	private Date invitedDate;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "status")
	private String status;

	//getters and setters
	public String getUserInviteId() {
		return userInviteId;
	}

	public void setUserInviteId(String userInviteId) {
		this.userInviteId = userInviteId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(String invitedBy) {
		this.invitedBy = invitedBy;
	}

	public Date getInvitedDate() {
		return invitedDate;
	}

	public void setInvitedDate(Date invitedDate) {
		this.invitedDate = invitedDate;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
