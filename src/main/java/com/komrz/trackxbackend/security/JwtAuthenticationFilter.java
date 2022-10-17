package com.komrz.trackxbackend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.komrz.trackxbackend.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
	
	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String requestTokenHeader = request.getHeader("Authorization");
		String userId = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				userId = tokenProvider.getUserIdFromJWT(jwtToken);
				String tenantId = tokenProvider.getTenantIdFromJWT(jwtToken);
				LOG.debug("User ID: " + userId);
				LOG.debug("Tenant ID: " + tenantId);
			} catch (IllegalArgumentException e) {
				request.setAttribute("error", "Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				request.setAttribute("error", "JWT Token has expired");
			} catch (SignatureException ex) {
				request.setAttribute("error", "Invalid JWT signature");
	        } catch (MalformedJwtException ex) {
	        	request.setAttribute("error", "Invalid JWT token");
	        } catch (UnsupportedJwtException ex) {
	        	request.setAttribute("error", "Unsupported JWT token");
	        }
		} else {
			request.setAttribute("error", "JWT Token does not begin with Bearer String");
		}
		// Once we get the token validate it.
		try {
			if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserPrincipal userPrincipal = customUserDetailsService.fetchUserById(userId);
				if (tokenProvider.validateToken(jwtToken)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userPrincipal, 
							SecurityContextHolder.getContext().getAuthentication(), 
							userPrincipal.getAuthorities());
					usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		} catch (Exception e) {
			logger.error("Could not set User authentication in security context", e);
		}
        filterChain.doFilter(request, response);
	}
}
