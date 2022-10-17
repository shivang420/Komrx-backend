package com.komrz.trackxbackend.controller;

import java.util.Iterator;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.komrz.trackxbackend.dto.EventCreateDTO;
import com.komrz.trackxbackend.enumerator.EventTypeEn;
import com.komrz.trackxbackend.exception.BadRequestException;
import com.komrz.trackxbackend.exception.ConflictException;
import com.komrz.trackxbackend.exception.NotFoundException;
import com.komrz.trackxbackend.exception.SuccessResponse;
import com.komrz.trackxbackend.model.EventPOJO;
import com.komrz.trackxbackend.security.UserPrincipal;
import com.komrz.trackxbackend.service.CustomUserDetailsService;
import com.komrz.trackxbackend.service.EventService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@RestController
@RequestMapping()
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	Logger log = LoggerFactory.getLogger(EventController.class);

	/**
	 * 
	 * @return
	 */
	@ApiOperation(value = "Get Event Type En", code = 200)
	@GetMapping(value = "/event/type")
	public ResponseEntity<?> getEventTypeEn(){
		return ResponseEntity.ok(EventTypeEn.values());
	}
	
	/**
	 * 
	 * @param contractId
	 * @return
	 */
	@ApiOperation(value = "Get All Events By Event Type", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List of Events Returned!", response = EventPOJO[].class),
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/event")
	public ResponseEntity<?> getAllEvents(@RequestParam(value = "eventType") EventTypeEn eventType) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		return ResponseEntity.status(HttpStatus.OK)
			.body(eventService.fetchEventByEventTypeEventPOJOs(eventType, tenantId));
	}

	/**
	 * 
	 * @param eventId
	 * @return
	 */
	@ApiOperation(value = "Get Event Name by Id", code = 200, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Event Returned!", response = EventPOJO.class),
			@ApiResponse(code = 404, message = "Event does not exists!")
	})
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping( value = "/event/{id}")
	public ResponseEntity<?> fetchEventById(@PathVariable(value = "id") String eventId) {
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String tenantId = userPrincipal.getTenantId();
		if(eventService.isExist(eventId, tenantId)) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(eventService.fetchEventId(eventId));
		}
		throw new NotFoundException("Event does not exists!", "Event does not exists!");
	}
	
	/**
	 * 
	 * @param eventCreateDTO
	 * @return
	 */
	@ApiOperation(value = "Add New Event", code = 201, authorizations = {@Authorization("JWT")})
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully Created!"),
			@ApiResponse(code = 404, message = "Parent Event does not exists!"),
			@ApiResponse(code = 404, message = "User does not exists!"),
			@ApiResponse(code = 409, message = "Duplicate Event Name!")
	})
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping( value = "/event/new")
	public ResponseEntity<?> createEvent(@ModelAttribute @Valid EventCreateDTO eventCreateDTO,
										@RequestParam MultipartFile[] eventFiles) {	
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		UserPrincipal userPrincipal =  (UserPrincipal) authentication.getPrincipal();
		String userId = userPrincipal.getId();
		String tenantId = userPrincipal.getTenantId();
		Iterator<String> iterator = eventCreateDTO.getEventUsers().iterator();
		while (iterator.hasNext()) {
			if(! customUserDetailsService.isExist(iterator.next(), tenantId)) {
				throw new NotFoundException("User does not exists!", "User does not exists!");
			}
		}
		//Add the existing user to the UserList
		eventCreateDTO.getEventUsers().add(userId);
		boolean isExist = eventService.isExist(eventCreateDTO, tenantId);
		if(! isExist ) {	
			String eventParentId = eventCreateDTO.getParentEventId();
			if(eventParentId != null) {
				boolean isExistParent = eventService.isExist(eventParentId, tenantId);
				if(! isExistParent ) {
					throw new NotFoundException("Parent Event does not exists!", "Parent Event does not exists!");
				}
			}
			String eventId;
			try{
				log.info("Creating a new event.");
				eventId = eventService.createdEvent(eventCreateDTO, userId, tenantId, eventFiles.length);
				log.info("Event created successfully ", eventId);
				try {
					log.info("Uploading event documents for event id ", eventId);
					eventService.uploadEventDocuments(eventFiles, eventId);
					log.info("Uploaded event documents successfully for event id ", eventId);
				} catch (Exception e) {
					log.error("Actual Error Message: ", e.getMessage());
					throw new BadRequestException("Supporting document not uploaded successfully.", e.getMessage());
				}

			} catch(Exception e){
				log.error("Actual Error Message: ", e.getMessage());
				throw new BadRequestException("Not able to create the Event.", "Not able to create the Event.");
			}
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new SuccessResponse(HttpStatus.CREATED, "Successfully Created!"));
			
		}
		throw new ConflictException("Duplicate Event Name!", "Duplicate Event Name!");
	}
}
