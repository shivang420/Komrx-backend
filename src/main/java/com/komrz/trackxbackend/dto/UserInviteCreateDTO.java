package com.komrz.trackxbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserInviteCreateDTO {

	@Email(message = "Please enter a valid Email!")
	@NotBlank(message = "Email cannot be blank!")
	private String email;
	
	@NotBlank(message = "Role Id cannot be blank!")
	private String role;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
