package com.komrz.trackxbackend.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class JwtTokenProvider {
	
	private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.jwtSecret}")
    private String jwtSecret;
	
	@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
	
	public String generateToken(Authentication authentication) {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date expiryDate = new Date(System.currentTimeMillis() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getId())
                .setIssuer(userPrincipal.getTenantId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
	
	
	public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
	
	public String getTenantIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getIssuer();
    }
	
	public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
        	LOG.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
        	LOG.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
        	LOG.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
        	LOG.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
        	LOG.error("JWT claims string is empty.");
        }
        return false;
    }
}
