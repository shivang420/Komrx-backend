package com.komrz.trackxbackend.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate Resource Name!")
public class ConflictException extends RuntimeException {

private static final long serialVersionUID = 6775142156493365875L;
	
	private String message;
	private List<String> errors;

	public ConflictException(String message, List<String> error) {
	    super(message);
	    this.message = message;
	    this.errors = error;
	}
	
	public ConflictException(String message, String error) {
	    super(message);
	    this.message = message;
	    this.errors = Arrays.asList(error);
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
