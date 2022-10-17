package com.komrz.trackxbackend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.komrz.trackxbackend.dto.UserInviteCreateDTO;
import com.komrz.trackxbackend.model.UserInvitePOJO;
import com.komrz.trackxbackend.repository.UserInviteRepository;

@Service
public class UserInviteService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
    private SpringTemplateEngine templateEngine;
	
	@Autowired
	private UserInviteRepository userInviteRepository;
	
	private final String invite = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789" ;
	
	public String sendInviteEmail(String username, String uniqueId) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		Context context = new Context();
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("url", uniqueId);
		context.setVariables(model);
		String html = templateEngine.process("user_invite_email", context);
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(username);
		helper.setSubject("Please Verify Your Email");
		helper.setText(html, true);
		emailSender.send(message);
        return "Successfully Snet Email";
	}
	
	public String generateUserInviteId(int count) {
		boolean check = true;
		String id = null;
		while (check == true) {
			id = generateString(count);
			check = userInviteRepository.existsById(id);
		}
		return id;
	}
	private String generateString(int count) {
		StringBuilder builder = new StringBuilder();
		while(count-- != 0) {
			int character = (int)(Math.random() * invite.length());
			builder.append(invite.charAt(character));
		}
		return builder.toString();
	}
	
	public String inviteUser(UserInviteCreateDTO userInviteCreateDTO, String userInviteId, String userId, String tenantId) {
		UserInvitePOJO userInvite = new UserInvitePOJO();
		userInvite.setUserInviteId(userInviteId);
		userInvite.setEmail(userInviteCreateDTO.getEmail().toLowerCase());
		userInvite.setInvitedBy(userId);
		userInvite.setInvitedDate(new Date(System.currentTimeMillis()));
		userInvite.setRole(userInviteCreateDTO.getRole());
		userInvite.setStatus("Y");
		userInvite.setTenantId(tenantId);
		return userInviteRepository.save(userInvite).getUserInviteId();
	}
	
	public UserInvitePOJO fetchUserInviteById(String userInviteId) {
		UserInvitePOJO userInvite = userInviteRepository.findByUserInviteId(userInviteId);
		if(userInvite == null) {
			return null;
		}
		if(userInvite.getInvitedDate().before(new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)))) {
			userInviteRepository.deleteById(userInviteId);
			return null;
		}
		if(userInvite.getStatus().equals("Y")) {
			return userInvite;
		}
		return null;
	}
	
	public void cancelInvite(String userInviteId) {
		userInviteRepository.deleteById(userInviteId);
	}

	public boolean isExist(String email) {
		UserInvitePOJO userInvite = userInviteRepository.findByEmail(email);
		if(userInvite == null) {
			return false;
		}
		if(userInvite.getInvitedDate().before(new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)))) {
			userInviteRepository.deleteById(userInvite.getUserInviteId());
			return false;
		}
		return true;
	}
	
	public void sendForgotPasswordEmail(String username, String jwtToken) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		Context context = new Context();
		Map<String, Object> model = new HashMap<String, Object>();
        model.put("url", jwtToken);
		context.setVariables(model);
		String html = templateEngine.process("user_forgot_password_email", context);
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(username);
		helper.setSubject("Please Verify Your Email");
		helper.setText(html, true);
		emailSender.send(message);
	}
}
