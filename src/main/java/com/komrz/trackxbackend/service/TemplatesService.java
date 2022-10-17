package com.komrz.trackxbackend.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.komrz.trackxbackend.dto.TemplatesDTO;
import com.komrz.trackxbackend.model.TemplateContractsPOJO;
import com.komrz.trackxbackend.model.TemplateGuidesPOJO;
import com.komrz.trackxbackend.repository.TemplateContractsRepository;
import com.komrz.trackxbackend.repository.TemplateGuideRepository;

@Service
public class TemplatesService {
	
	@Value("${spring.aws.accessKeyId}")
	private String awsAccessKeyId;

	@Value("${spring.aws.secretKeyId}")
	private String awsSecretKeyId;
	
	@Value("${spring.aws.templateBucketName}")
	private String bucketName;
	
	@Value("${spring.aws.contractsFolderName}")
	private String contractFolderName;
	
	@Value("${spring.aws.guidesFolderName}")
	private String guideFolderName;

	@Autowired
	private TemplateContractsRepository templateContractsRepository;
	
	@Autowired
	private TemplateGuideRepository templateGuideRepository;
	
	public void uploadTemplateContract(MultipartFile templateFile, String name, String tenantId) {
		TemplateContractsPOJO newTemplate = new TemplateContractsPOJO();
		newTemplate.setName(name);
		newTemplate.setTenantId(tenantId);
		String templateId = templateContractsRepository.save(newTemplate).getId();
		uploadToS3(templateFile, templateId, 0);
	}
	
	public void uploadTemplateGuide(MultipartFile templateFile, String name, String tenantId) {
		TemplateGuidesPOJO newTemplate = new TemplateGuidesPOJO();
		newTemplate.setName(name);
		newTemplate.setTenantId(tenantId);
		String templateId = templateGuideRepository.save(newTemplate).getId();
		uploadToS3(templateFile, templateId, 1);
	}
	
	private void uploadToS3(MultipartFile templateFile, String name, int check) {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();
		String objectName = "/" + name;
		if(check == 0) {
			objectName = contractFolderName + objectName;
		}else {
			objectName = guideFolderName + objectName;
		}
		try {
			File convFile = multipartToFile(templateFile, name);
			s3client.putObject(
					  bucketName, 
					  objectName,
					  convFile
					  );
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	public List<TemplatesDTO> getAllTemplateContracts(String tenantId) {
		List<TemplateContractsPOJO> templateContracts = templateContractsRepository.findByTenantId("ALL");
		templateContracts.addAll(templateContractsRepository.findByTenantId(tenantId));
		List<TemplatesDTO> templates = new ArrayList<>();
		templateContracts.forEach((temp) -> {
			templates.add(getTemplateDTO(temp));
		});
		return templates;
	}
	
	public List<TemplatesDTO> getAllTemplateGuides(String tenantId) {
		List<TemplateGuidesPOJO> templateGuides = templateGuideRepository.findByTenantId("ALL");
		templateGuides.addAll(templateGuideRepository.findByTenantId(tenantId));
		List<TemplatesDTO> templates = new ArrayList<>();
		templateGuides.forEach((temp) -> {
			templates.add(getTemplateDTO(temp));
		});
		return templates;
	}
	
	private TemplatesDTO getTemplateDTO(TemplateContractsPOJO templateContract) {
		TemplatesDTO templates = new TemplatesDTO();
		templates.setTemplateId(templateContract.getId());
		templates.setTemplateName(templateContract.getName());
		templates.setPresignedUrl(getPreSignedUrl(templateContract.getId(), 0));
		return templates;
	}
	
	private TemplatesDTO getTemplateDTO(TemplateGuidesPOJO templateGuides) {
		TemplatesDTO templates = new TemplatesDTO();
		templates.setTemplateId(templateGuides.getId());
		templates.setTemplateName(templateGuides.getName());
		templates.setPresignedUrl(getPreSignedUrl(templateGuides.getId(), 1));
		return templates;
	}
	
	private URL getPreSignedUrl(String temaplateId, int check) {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();

        // Set the presigned URL to expire after one hour.
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        String objectKey = "/" + temaplateId ;
        if(check == 0) {
        	objectKey = contractFolderName + objectKey;
		}else {
			objectKey = guideFolderName + objectKey;
		}
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
		return url;
	}
	
	public boolean isExistContractByName(String templateName, String tenantId) {
		boolean check = false;
		List<TemplateContractsPOJO> templateContracts = templateContractsRepository.findByTenantId("ALL");
		templateContracts.addAll(templateContractsRepository.findByTenantId(tenantId));
		ListIterator<TemplateContractsPOJO> listIterator = templateContracts.listIterator();
		while (listIterator.hasNext()) {
			TemplateContractsPOJO nextTemplate = listIterator.next();
			if(nextTemplate.getName().equals(templateName)) {
				check = true;
			}
		}
		return check;
	}
	
	public boolean isExistGuideByName(String templateName, String tenantId) {
		boolean check = false;
		List<TemplateGuidesPOJO> templateGuides = templateGuideRepository.findByTenantId("ALL");
		templateGuides.addAll(templateGuideRepository.findByTenantId(tenantId));
		ListIterator<TemplateGuidesPOJO> listIterator = templateGuides.listIterator();
		while (listIterator.hasNext()) {
			TemplateGuidesPOJO nextTemplate = listIterator.next();
			if(nextTemplate.getName().equals(templateName)) {
				check = true;
			}
		}
		return check;
	}
	
	public boolean isExistContractForDelete(String templateId, String tenantId) {
		if(templateContractsRepository.existsById(templateId)) {
			TemplateContractsPOJO template = templateContractsRepository.getOne(templateId);
			if(template.getTenantId().equals(tenantId)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isExistGuideForDelete(String templateId, String tenantId) {
		if(templateGuideRepository.existsById(templateId)) {
			TemplateGuidesPOJO template = templateGuideRepository.getOne(templateId);
			if(template.getTenantId().equals(tenantId)) {
				return true;
			}
		}
		return false;
	}
	
	public void deleteContract(String templateId) {
		templateContractsRepository.deleteById(templateId);
		deleteFromBucket(templateId, 0);
	}
	
	public void deleteGuide(String templateId) {
		templateGuideRepository.deleteById(templateId);
		deleteFromBucket(templateId, 1);
	}
	
	private void deleteFromBucket(String templateId, int check) {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();
		String objectKey = "/" + templateId;
		if(check == 0) {
        	objectKey = contractFolderName + objectKey;
		}else {
			objectKey = guideFolderName + objectKey;
		}
		try {
			s3client.deleteObject(bucketName, objectKey);
		} catch (Exception e) {
			
		}
	}
}
