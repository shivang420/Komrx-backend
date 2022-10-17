package com.komrz.trackxbackend.dto;

import java.net.URL;

public class TemplatesDTO {

	private String templateId;
	private String templateName;
	private URL presignedUrl;
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public URL getPresignedUrl() {
		return presignedUrl;
	}
	public void setPresignedUrl(URL presignedUrl) {
		this.presignedUrl = presignedUrl;
	}
}
