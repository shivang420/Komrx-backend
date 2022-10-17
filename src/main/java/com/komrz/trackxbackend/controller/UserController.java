package com.komrz.trackxbackend.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.komrz.trackxbackend.dto.UserCreateDTO;
import com.komrz.trackxbackend.dto.UserFetchDTO;
import com.komrz.trackxbackend.dto.UserInviteCreateDTO;
import com.komrz.trackxbackend.exception.BadRequestException;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.exception.UnauthorizedException;
import com.komrz.trackxbackend.model.User;
import com.komrz.trackxbackend.model.UserInvitePOJO;
import com.komrz.trackxbackend.security.ForgotPassJwtToken;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CustomUserDetailsService;
import com.komrz.trackxbackend.service.UserInviteService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@RestController
@RequestMapping()
public class UserController {
	
	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	private UserInviteService userInviteService;
	
	@Autowired
	private ForgotPassJwtToken forgotPassJwtToken;

	@ApiOperation(value = "Get All Users", code = 200, authorizations = {@Authorization("JWT")})
	@GetMapping( value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UserFetchDTO>> getAllUsers() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.ok(userService.fetchUsersByTenantId(tenantId));
	}
	
	@ApiOperation(value = "Get User Info", code = 200, authorizations = {@Authorization("JWT")})
	@GetMapping( value = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserInfo() {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		return ResponseEntity.ok(userService.fetchUser(userPrincipal.getUser()));
	}
	
	@ApiOperation(value = "Invite New User", code = 200, authorizations = {@Authorization("JWT")})
	@PostMapping(value = "/user/invite")
	public ResponseEntity<?> inviteUser(@RequestBody @Valid UserInviteCreateDTO userInviteCreateDTO){
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String userId = userPrincipal.getId();
		String tenantId = userPrincipal.getTenantId();
		System.out.println(authentication.getAuthorities().toString());
		try {
			if(userService.loadUserByUsername(userInviteCreateDTO.getEmail().toLowerCase()) != null) {
				throw new ConflictException("User Already Exists", "User Already Exists");
			}
		} catch (UsernameNotFoundException e) {
		}
		if(userInviteService.isExist(userInviteCreateDTO.getEmail().toLowerCase())) {
			throw new ConflictException("User Invite Already Exists", "User Invite Already Exists");
		}
		String userInviteId = userInviteService.generateUserInviteId(10);
		userInviteService.inviteUser(userInviteCreateDTO, userInviteId, userId, tenantId);
		try {
			userInviteService.sendInviteEmail(userInviteCreateDTO.getEmail().toLowerCase(), userInviteId);
		} catch (MessagingException e) {
			throw new BadRequestException("Error", "Error");
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Invited!"));
	}
	
	@ApiOperation(value = "Get Invited User")
	@GetMapping(value = "/user/invite/{id}")
	public ResponseEntity<?> getInvitedUser(@PathVariable(value = "id")String userInviteId){
		UserInvitePOJO userInvite = userInviteService.fetchUserInviteById(userInviteId);
		if(userInvite == null) {
			throw new NotFoundException("User Invitation Expired", "User Invitation Expired");
		}
		return ResponseEntity.ok(userInvite);
	}
	
	@ApiOperation(value = "Add New User")
	@PostMapping(value = "/user/new/{id}")
	public ResponseEntity<?> addNewUser(@PathVariable(value = "id")String userInviteId,
								@RequestBody @Valid UserCreateDTO userCreateDTO){
		UserInvitePOJO userInvite = userInviteService.fetchUserInviteById(userInviteId);
		if(userInvite == null) {
			throw new NotFoundException("User Invitation Expired", "User Invitation Expired");
		}
		userService.createUser(userCreateDTO, 
				userInvite.getRole(), userInvite.getTenantId(), 
				userInvite.getEmail(), userInvite.getInvitedBy());
		userInviteService.cancelInvite(userInviteId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Added!"));
	}
	
	@ApiOperation(value = "Cancel An Invite", code = 200, authorizations = {@Authorization("JWT")})
	@GetMapping(value = "/user/invite/{id}/cancel")
	public ResponseEntity<?> cancelInvitation(@PathVariable(value = "id")String userInviteId){
		UserInvitePOJO userInvite = userInviteService.fetchUserInviteById(userInviteId);
		if(userInvite == null) {
			return ResponseEntity.ok("User Invitation Expired");
		}
		userInviteService.cancelInvite(userInviteId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Canceled!"));
	}
	
	@ApiOperation(value = "Forgot Password")
	@PostMapping(value = "/user/forgot")
	public ResponseEntity<?> forgotPassword(@RequestParam(value = "email")String email){
		User user = userService.getUserFromEmail(email);
		if(user == null) {
			throw new NotFoundException("Please enter valid email", "Account Does Not Exist With Email");
		}
		String jwtToken = forgotPassJwtToken.generateToken(user);
		try {
			userInviteService.sendForgotPasswordEmail(email.toLowerCase(), jwtToken);
		} catch (MessagingException e) {
			throw new NotFoundException("Please enter valid email", "Account Does Not Exist With Email");
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Sent!"));
	}
	
	@ApiOperation(value = "Get Forgot Password Info")
	@GetMapping(value = "/user/forgot/info")
	public ResponseEntity<?> getInfo(@RequestHeader(value = "Authorization")String jwtToken, HttpServletRequest request){
		String userId = (String) request.getAttribute("userId");
		if(userId == null) {
			throw new UnauthorizedException("Invalid Token", "Invalid Token");
		}
		return ResponseEntity.ok(userService.getUserFromId(userId).getUsername());
	}
	
	@ApiOperation(value = "Change Password")
	@PostMapping(value = "/user/forgot/change")
	public ResponseEntity<?> changePass(@RequestHeader(value = "Authorization")String jwtToken, 
										@RequestParam(value = "Password")String password,
										HttpServletRequest request){
		String userId = (String) request.getAttribute("userId");
		if(userId == null) {
			throw new UnauthorizedException("Invalid Token", "Invalid Token");
		}
		userService.changePassword(userId, password);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Changed!"));
	}
}
