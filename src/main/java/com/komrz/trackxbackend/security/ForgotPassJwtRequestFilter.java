package com.komrz.trackxbackend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import com.komrz.trackxbackend.model.User;
import com.komrz.trackxbackend.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@WebFilter(urlPatterns = {
		"/user/forgot/info",
		"/user/forgot/change"
})
public class ForgotPassJwtRequestFilter extends OncePerRequestFilter{
	
	private static final Logger LOG = LoggerFactory.getLogger(ForgotPassJwtRequestFilter.class);
	
	@Autowired
	private ForgotPassJwtToken forgotPassJwtRequestFilter;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws ServletException, IOException {
		LOG.debug("Entered Filter");
		String jwtToken = request.getHeader("Authorization");
		String userId = null;
		if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
			jwtToken = jwtToken.substring(7);
			try {
				userId = forgotPassJwtRequestFilter.getUserIdFromJWT(jwtToken);
			} catch (IllegalArgumentException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
			} catch (SignatureException ex) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
	        } catch (MalformedJwtException ex) {
	        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
	        } catch (UnsupportedJwtException ex) {
	        	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unsupported JWT token");
	        }
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT Token does not begin with Bearer String");
		}
		if(userId != null) {
			User user = customUserDetailsService.getUserFromId(userId);
			if(user == null) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unsupported JWT token");
			}else {
				if(forgotPassJwtRequestFilter.validateToken(user, jwtToken)) {
					request.setAttribute("userId", userId);
				}
			}
		}
		chain.doFilter(request, response);
	}
}
