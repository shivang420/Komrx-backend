package com.komrz.trackxbackend.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.komrz.trackxbackend.dto.ContractFetchDTO;
import com.komrz.trackxbackend.dto.EventCreateDTO;
import com.komrz.trackxbackend.enumerator.EventTypeEn;
import com.komrz.trackxbackend.model.EventPOJO;
import com.komrz.trackxbackend.model.User;
import com.komrz.trackxbackend.repository.EventRepository;
import com.komrz.trackxbackend.repository.UserRepository;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContractService contractService;

	@Value("${spring.aws.accessKeyId}")
	private String awsAccessKeyId;

	@Value("${spring.aws.secretKeyId}")
	private String awsSecretKeyId;
	
	@Value("${spring.aws.eventsBucketName}")
	private String bucketName;
	
	public void uploadEventDocuments(MultipartFile[] supportingFiles, String contractId) throws IllegalStateException, IOException {
		System.out.println("Upload Supporting Documents :" + supportingFiles.length);
		
		int i=1;
		while (i<=supportingFiles.length) {
			String name = contractId + "_" + i;
			uploadSingleEventDocuemnt(supportingFiles[i-1], name);
			i++;
		}
	}
	
	public void uploadSingleEventDocuemnt(MultipartFile supportingFile, String name) throws IllegalStateException, IOException {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();
		File convFile = multipartToFile(supportingFile, name);
		s3client.putObject(
				  bucketName, 
				  name, 
				  convFile
				  );
		System.out.println("Event Files being uploaded :"  + name);
	}
	
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	public String createdEvent(EventCreateDTO eventCreateDTO, String userId, String tenantId, int count) {
		EventPOJO event = new EventPOJO();
		event.setEventTitle(eventCreateDTO.getEventTitle());
		event.setEventDescription(eventCreateDTO.getEventDescription());
		event.setStartDate(eventCreateDTO.getStartDate());
		event.setEndDate(eventCreateDTO.getEndDate());
		event.setIsFullDayEvent(eventCreateDTO.getIsFullDayEvent());
		event.setIsRecurring(eventCreateDTO.getIsRecurring());
		event.setCreatedBy(userId);
		event.setCreatedDate(new Date(System.currentTimeMillis()));
		event.setContractId(eventCreateDTO.getContractId());
		event.setParentEventId(eventCreateDTO.getParentEventId());
		event.setIsPrivate(eventCreateDTO.getIsPrivate());
		event.setConferenceLink(eventCreateDTO.getConferenceLink());
		event.setLocation(eventCreateDTO.getLocation());
		event.setEventTypeEn(eventCreateDTO.getEventTypeEn());
		event.setCount(count);
		Iterator<String> eventUser = eventCreateDTO.getEventUsers().iterator();
		Set<User> users = new HashSet<User>();
		while (eventUser.hasNext()) {
			users.add(userRepository.getOne(eventUser.next()));
		}
		event.setEventUsers(users);
		
		return eventRepository.save(event).getId();
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(String eventId, String tenantId) {
		EventPOJO event = eventRepository.findByEventId(eventId);
		if(event == null) {
			return false;
		}
		if(contractService.isExist(event.getContractId(), tenantId)) {
			return true;
		}
		return false;
	}
	
	public List<EventPOJO> fetchEventByEventTypeEventPOJOs(EventTypeEn eventType, String tenantId) {
		List<ContractFetchDTO> contracts = contractService.fetchContractTenantId(tenantId);
//		List<EventPOJO>
//		return eventRepository.findByEventTypeEnAndTenantId(eventType, tenantId);
		return null;
	}
	
	public EventPOJO fetchEventId(String eventId) {
		return eventRepository.findByEventId(eventId);
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(EventCreateDTO eventCreateDTO, String tenantId) {
		if(! contractService.isExist(eventCreateDTO.getContractId(), tenantId)) {
			return false;
		}
		String id = eventRepository.isExist(eventCreateDTO.getEventTitle(), eventCreateDTO.getContractId());
		if(id == null) {
			return false;
		}
		return true;
	}
}
