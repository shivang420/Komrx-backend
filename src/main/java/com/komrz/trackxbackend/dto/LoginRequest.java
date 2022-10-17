package com.komrz.trackxbackend.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Please enter a valid email")
    private String usernameOrEmail;

	@NotBlank(message = "Passwrod cannot be blank")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
