package com.komrz.trackxbackend.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import com.komrz.trackxbackend.dto.ContractCreateDTO;
import com.komrz.trackxbackend.dto.ContractFetchDTO;
import com.komrz.trackxbackend.dto.ContractFetchDocDTO;
import com.komrz.trackxbackend.model.ContractPOJO;
import com.komrz.trackxbackend.repository.ContractCategoryRepository;
import com.komrz.trackxbackend.repository.ContractRepository;
import com.komrz.trackxbackend.repository.ContractSubCategoryRepository;
import com.komrz.trackxbackend.repository.FetchRepository;
import com.komrz.trackxbackend.repository.ProjectRepository;
import com.komrz.trackxbackend.repository.VendorRepository;
import com.komrz.trackxbackend.repository.WbsRepository;

@Service
public class ContractService {
	
	@Autowired
	private ContractRepository contractRepository;
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private WbsRepository wbsRepository;
	
	@Autowired
	private ContractSubCategoryRepository contractSubCategoryRepository;
	
	@Autowired
	private ContractCategoryRepository contractCategoryRepository;
	
	@Autowired
	private FetchRepository fetchRepository;
	
	@Value("${spring.aws.accessKeyId}")
	private String awsAccessKeyId;

	@Value("${spring.aws.secretKeyId}")
	private String awsSecretKeyId;
	
	@Value("${spring.aws.bucketName}")
	private String bucketName;
	
	@Value("${spring.aws.supportingFolderName}")
	private String folderName;


	public String uploadContract(MultipartFile contractFile, String contractId) {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();
		try {
			File convFile = multipartToFile(contractFile, contractId);
			s3client.putObject(
					  bucketName, 
					  contractId, 
					  convFile
					  );
		}catch(IOException e) {
			e.printStackTrace();
		}
		return contractId ;
	}
	
	public void uploadSupportingDocument(MultipartFile[] supportingFiles, String contractId) throws IllegalStateException, IOException {
		System.out.println("Upload Supporting Documents :" + supportingFiles.length);
		
		int i=1;
		while (i<=supportingFiles.length) {
			String name = contractId + "_" + i;
			uploadSingleSupport(supportingFiles[i-1], name);
			i++;
		}
	}
	
	public void uploadSingleSupport(MultipartFile supportingFile, String name) throws IllegalStateException, IOException {
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKeyId,awsSecretKeyId);
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(cred))
				  .withRegion(Regions.AP_SOUTH_1)
				  .build();
		File convFile = multipartToFile(supportingFile, name);
		s3client.putObject(
				  bucketName, 
				  folderName + "/" + name, 
				  convFile
				  );
	}
	
	public ContractFetchDocDTO fetchContractDocument(String contractId) {
		ContractPOJO contract = contractRepository.getOne(contractId);
		int support = contract.getSupportingDocumentCount();
		ContractFetchDocDTO response = new ContractFetchDocDTO();
		response.setContractFile(getContractFile(contractId));
		response.setSupportingDocumentCount(support);
		List<URL> supportFiles = new ArrayList<>();
		while (support > 0) {
			supportFiles.add(getSupportDoc(contractId + "_" + support));
			support = support - 1;
		}
		response.setSupportingFiles(supportFiles);
		return response;
	}
	
	private URL getContractFile(String contractId) {
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
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, contractId)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
		return url;
	}
	
	private URL getSupportDoc(String objectKey) {
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
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, folderName + "/" + objectKey)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
		return url;
	}
	
	public  static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}

	public String createContract(ContractCreateDTO contractCreateDTO, String userId, String tenantId, int supportingCount) {
		ContractPOJO newContract = new ContractPOJO();
		newContract.setContractName(contractCreateDTO.getContractName());
		newContract.setContractStatus(contractCreateDTO.getContractStatus());
		newContract.setVendor(vendorRepository.getOne(contractCreateDTO.getVendorId()));
		newContract.setWbs(wbsRepository.getOne(contractCreateDTO.getWbsId()));
		newContract.setCostCenterId(contractCreateDTO.getCostCenterId());
		newContract.setTenantId(tenantId);
		newContract.setContractStartDate(contractCreateDTO.getContractStartDate());
		newContract.setContractEndDate(contractCreateDTO.getContractEndDate());
		newContract.setContractRenewalDate(contractCreateDTO.getContractRenewalDate());
		newContract.setContractTerminationDate(contractCreateDTO.getContractTerminationDate());
		newContract.setVendorManager(contractCreateDTO.getVendorManager());
		newContract.setBusinessPartnerName(contractCreateDTO.getBusinessPartnerName());
		newContract.setContractFormatEn(contractCreateDTO.getContractFormatEn());
		newContract.setContractTypeEn(contractCreateDTO.getContractTypeEn());
		newContract.setProject(projectRepository.getOne(contractCreateDTO.getProjectId()));
		newContract.setNotes(contractCreateDTO.getNotes());
		newContract.setUploadedBy(userId);
		newContract.setUploadedDate(new Date(System.currentTimeMillis()));
		newContract.setContractSubCategory(contractSubCategoryRepository.getOne(contractCreateDTO.getContractSubCategoryId()));
		newContract.setContractCategory(contractCategoryRepository.getOne(contractCreateDTO.getContractCategoryId()));
		newContract.setBuySellEn(contractCreateDTO.getBuySellEn());
		newContract.setSupportingDocumentCount(supportingCount);
		
		return contractRepository.save(newContract).getId();
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(String contractId, String tenantId) {
		ContractPOJO contract = contractRepository.findByContractId(contractId);
		if(contract == null) {
			return false;
		}
		if(tenantId.equals(contract.getTenantId())) {
			return true;
		}
		return false;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExistByName(String contractName, String tenantId) {
		List<ContractPOJO> contract = contractRepository.findByContractName(contractName);
		if (contract.isEmpty()) {
			return false;
		}
		ListIterator<ContractPOJO> listIterator = contract.listIterator();
		while (listIterator.hasNext()) {
			if(tenantId.equals(listIterator.next().getTenantId())) {
				return true;
			}
		}
		return false;
	}
	
	public String updateContract(String contractId, ContractCreateDTO contractCreateDTO, String userId) {
		ContractPOJO contractPOJO = contractRepository.getOne(contractId);
		contractPOJO.setContractName(contractCreateDTO.getContractName());
		contractPOJO.setContractStatus(contractCreateDTO.getContractStatus());
		contractPOJO.setVendor(vendorRepository.getOne(contractCreateDTO.getVendorId()));
		contractPOJO.setWbs(wbsRepository.getOne(contractCreateDTO.getWbsId()));
		contractPOJO.setCostCenterId(contractCreateDTO.getCostCenterId());
		contractPOJO.setContractStartDate(contractCreateDTO.getContractStartDate());
		contractPOJO.setContractEndDate(contractCreateDTO.getContractEndDate());
		contractPOJO.setContractRenewalDate(contractCreateDTO.getContractRenewalDate());
		contractPOJO.setContractTerminationDate(contractCreateDTO.getContractTerminationDate());
		contractPOJO.setVendorManager(contractCreateDTO.getVendorManager());
		contractPOJO.setBusinessPartnerName(contractCreateDTO.getBusinessPartnerName());
		contractPOJO.setContractFormatEn(contractCreateDTO.getContractFormatEn());
		contractPOJO.setContractTypeEn(contractCreateDTO.getContractTypeEn());
		contractPOJO.setProject(projectRepository.getOne(contractCreateDTO.getProjectId()));
		contractPOJO.setNotes(contractCreateDTO.getNotes());
		contractPOJO.setUploadedBy(userId);
		contractPOJO.setUploadedDate(new Date(System.currentTimeMillis()));
		contractPOJO.setContractSubCategory(contractSubCategoryRepository.getOne(contractCreateDTO.getContractSubCategoryId()));
		contractPOJO.setContractCategory(contractCategoryRepository.getOne(contractCreateDTO.getContractCategoryId()));
		contractPOJO.setBuySellEn(contractCreateDTO.getBuySellEn());
		
		return contractRepository.save(contractPOJO).getId();	
	}
	
	public ContractPOJO fetchContractId(String contractId) {
		return contractRepository.findByContractId(contractId);
	}
	
//	public List<ContractPOJO> fetchContractTenantId(String tenantId) {
//		return contractRepository.findByTenantId(tenantId);	
//	}
	
	public List<ContractFetchDTO> fetchContractTenantId(String tenantId) {
		return fetchRepository.getContract(tenantId);
	}
}
