package com.komrz.trackxbackend.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

private static final long serialVersionUID = -427607117908105018L;
	
	private String message;
	private List<String> errors;

	public BadRequestException(String message, List<String> error) {
	    super(message);
	    this.message = message;
	    this.errors = error;
	}
	
	public BadRequestException(String message, String error) {
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
