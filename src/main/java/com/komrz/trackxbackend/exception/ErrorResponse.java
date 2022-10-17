package com.komrz.trackxbackend.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String message;
	private HttpStatus status;
	private int code;
	private List<String> errors;
	
	public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        super();
        this.message = message;
        this.status = status;
        this.code = status.value();
        this.errors = errors;
    }
 
    public ErrorResponse(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        this.code = status.value();
        this.errors = Arrays.asList(error);
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

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
