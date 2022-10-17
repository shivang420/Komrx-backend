package com.komrz.trackxbackend.dto;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ContractFetchDocDTO {

	private URL contractFile;
	private int supportingDocumentCount;
	private List<URL> supportingFiles = new ArrayList<>();
	
	public URL getContractFile() {
		return contractFile;
	}
	public void setContractFile(URL contractFile) {
		this.contractFile = contractFile;
	}
	public int getSupportingDocumentCount() {
		return supportingDocumentCount;
	}
	public void setSupportingDocumentCount(int supportingDocumentCount) {
		this.supportingDocumentCount = supportingDocumentCount;
	}
	public List<URL> getSupportingFiles() {
		return supportingFiles;
	}
	public void setSupportingFiles(List<URL> supportingFiles) {
		this.supportingFiles = supportingFiles;
	}
}
