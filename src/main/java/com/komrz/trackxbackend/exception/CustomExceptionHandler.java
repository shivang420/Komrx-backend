package com.komrz.trackxbackend.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
								  MethodArgumentNotValidException ex, 
								  HttpHeaders headers, 
								  HttpStatus status, 
								  WebRequest request) {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	        errors.add(error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	        errors.add(error.getDefaultMessage());
	    }
	    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed!", errors);
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationExceptions(
			ConstraintViolationException ex) {
		StringBuilder str 
        = new StringBuilder(); 
		 ex.getConstraintViolations().forEach((cv) -> {
			 str.append(cv.getMessageTemplate() );
		});;
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, str.toString(), str.toString());
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Object> handleConflictException(ConflictException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), ex.getErrors());
		return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrors());
		return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getErrors());
		return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), ex.getErrors());
		return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, "One or more files are too large!", ex.getMessage());
		return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
  }
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
						  HttpRequestMethodNotSupportedException ex, 
						  HttpHeaders headers, 
						  HttpStatus status, 
						  WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getMethod());
	    builder.append(
	      " method is not supported for this request. Supported methods are ");
	    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
	 
	    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage(), builder.toString());
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
						  MissingServletRequestParameterException ex, HttpHeaders headers, 
						  HttpStatus status, WebRequest request) {
	    String error = ex.getParameterName() + " parameter is missing";
	    
	    ErrorResponse errorResponse = 
	      new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
						MethodArgumentTypeMismatchException ex, WebRequest request) {
	    String error = 
	      ex.getName() + " should be of type " + ex.getRequiredType().getName();
	 
	    ErrorResponse errorResponse = 
	      new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), error);
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
						  HttpMediaTypeNotSupportedException ex, 
						  HttpHeaders headers, 
						  HttpStatus status, 
						  WebRequest request) {
	    StringBuilder builder = new StringBuilder();
	    builder.append(ex.getContentType());
	    builder.append(" media type is not supported. Supported media types are ");
	    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
	 
	    ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 
	      ex.getMessage(), builder.substring(0, builder.length() - 2));
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(
	      HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), Arrays.asList( "Exception Cause: " + ex.getCause(), 
	    		  															"Exception Stack Trace" + ex.getStackTrace(),
	    		  															"Exception To String" + ex.toString()));
	    return new ResponseEntity<Object>(
	    		errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}
}
