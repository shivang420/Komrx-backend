package com.komrz.trackxbackend.exception;

import org.springframework.http.HttpStatus;

public class SuccessResponse {

	private String message;
	private HttpStatus status;
	private int code;
	
	public SuccessResponse(HttpStatus status, String message) {
        super();
        this.message = message;
        this.status = status;
        this.code = status.value();
    }
 
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
