package com.komrz.trackxbackend.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.komrz.trackxbackend.exception.ErrorResponse;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
//		LOG.error("Responding with unauthorized error. Message - {}", authException.getMessage());
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		response.setContentType("application/json") ;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, authException.getMessage(), (String)request.getAttribute("error"));
        final ObjectMapper mapper = new ObjectMapper() ;
        mapper.writeValue(response.getOutputStream(), errorResponse) ;
	}
}
