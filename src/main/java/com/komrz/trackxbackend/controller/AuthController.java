package com.komrz.trackxbackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.JwtAuthenticationResponse;
import com.komrz.trackxbackend.dto.LoginRequest;
import com.komrz.trackxbackend.exception.UnauthorizedException;
import com.komrz.trackxbackend.security.JwtTokenProvider;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    /**
     * 
     * @param loginRequest
     * @return
     */
    @ApiOperation(value = "Authenticate User")
//    @CrossOrigin(origins = "http://localhost:3000")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Jwt Token returned!", response = JwtAuthenticationResponse.class),
			@ApiResponse(code = 401, message = "Bad Credentials!")
	})
	@ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody @Valid LoginRequest loginRequest) {
    	try {
    		Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    		loginRequest.getUsernameOrEmail().toLowerCase(),
                    		loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.status(HttpStatus.OK)
            		.body(new JwtAuthenticationResponse(jwt));
		} catch (Exception e) {
			throw new UnauthorizedException("Please enter valid email address and password", "Unauthorized");
		}
        
    }
}
