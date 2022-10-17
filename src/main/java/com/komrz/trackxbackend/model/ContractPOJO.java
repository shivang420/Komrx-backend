package com.komrz.trackxbackend.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.komrz.trackxbackend.enumerator.BuySellEn;
import com.komrz.trackxbackend.enumerator.ContractFormatEn;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;
import com.komrz.trackxbackend.enumerator.ContractTypeEn;

@Entity
@Table(name = "contract", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContractPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_id")
	private String id;
	
	@Column(name = "contract_name")
	private String contractName;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private ContractStatusEn contractStatus;
	
//	@Column(name = "vendor_id")
//	private String vendorId;
	
	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
	@JsonIgnoreProperties("vendorContracts")
	private VendorPOJO vendor;
	
//	@Column(name = "wbs_id")
//	private String wbsId;
	
	@ManyToOne
	@JoinColumn(name = "wbs_id", referencedColumnName = "wbs_id")
	@JsonIgnoreProperties({"wbsContracts","costCenterContracts"})
	private WbsPOJO wbs;
	
	@Column(name = "cost_center_id")
	private String costCenterId;
	
	@Column(name = "tenant_id")
	private String tenantId;
	
	@Column(name = "contract_start_date")
	private Date contractStartDate;
	
	@Column(name = "contract_end_date")
	private Date contractEndDate;
	
	@Column(name = "contract_renewal_date")
	private Date contractRenewalDate;
	
	@Column(name = "contract_termination_date")
	private Date contractTerminationDate;
	
	@Column(name = "vendor_manager")
	private String vendorManager;
	
	@Column(name = "business_partner_name")
	private String businessPartnerName;
	
	@Column(name = "contract_format")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private ContractFormatEn contractFormatEn;
	
	@Column(name = "contract_type")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private ContractTypeEn contractTypeEn;
	
//	@Column(name = "project_id")
//	private String projectId;
	
	@ManyToOne
	@JoinColumn(name = "project_id", referencedColumnName = "project_id")
	@JsonIgnoreProperties({"projectContracts"})
	private ProjectPOJO project;
	
	@Column(name = "notes")
	private String notes;
	
	@Column(name = "uploaded_by")
	private String uploadedBy;
	
	@Column(name = "uploaded_date")
	private Date uploadedDate;
	
	@Column(name = "supporting_document_count")
	private int supportingDocumentCount;

//	@Column(name = "contract_sub_category_id")
//	private String contractSubCategoryId;
	
	@ManyToOne
	@JoinColumn(name = "contract_sub_category_id", referencedColumnName = "contract_sub_category_id")
	private ContractSubCategoryPOJO contractSubCategory;
	

//	@Column(name = "contract_category_id")
//	private String contractCategoryId;
	
	@ManyToOne
	@JoinColumn(name = "contract_category_id", referencedColumnName = "contract_category_id")
	private ContractCategoryPOJO contractCategory;
	
	@Column(name = "buy_sell")
	@Enumerated(EnumType.STRING)
	@Type(type = "enum_postgressql")
	private BuySellEn buySellEn;
	
	@OneToMany
	@JoinColumn(name = "contract_id")
	private Set<ContractBillInfoPOJO> contarctBillInfos;
	
	//getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public ContractStatusEn getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(ContractStatusEn contractStatus) {
		this.contractStatus = contractStatus;
	}
	
	public VendorPOJO getVendor() {
		return vendor;
	}

	public void setVendor(VendorPOJO vendor) {
		this.vendor = vendor;
	}	

	public WbsPOJO getWbs() {
		return wbs;
	}

	public void setWbs(WbsPOJO wbs) {
		this.wbs = wbs;
	}

	public String getCostCenterId() {
		return costCenterId;
	}

	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public Date getContractRenewalDate() {
		return contractRenewalDate;
	}

	public void setContractRenewalDate(Date contractRenewalDate) {
		this.contractRenewalDate = contractRenewalDate;
	}

	public Date getContractTerminationDate() {
		return contractTerminationDate;
	}

	public void setContractTerminationDate(Date contractTerminationDate) {
		this.contractTerminationDate = contractTerminationDate;
	}

	public String getVendorManager() {
		return vendorManager;
	}

	public void setVendorManager(String vendorManager) {
		this.vendorManager = vendorManager;
	}

	public String getBusinessPartnerName() {
		return businessPartnerName;
	}

	public int getSupportingDocumentCount() {
		return supportingDocumentCount;
	}

	public void setSupportingDocumentCount(int supportingDocumentCount) {
		this.supportingDocumentCount = supportingDocumentCount;
	}

	public void setBusinessPartnerName(String businessPartnerName) {
		this.businessPartnerName = businessPartnerName;
	}

	public ContractFormatEn getContractFormatEn() {
		return contractFormatEn;
	}

	public void setContractFormatEn(ContractFormatEn contractFormatEn) {
		this.contractFormatEn = contractFormatEn;
	}

	public ContractTypeEn getContractTypeEn() {
		return contractTypeEn;
	}

	public void setContractTypeEn(ContractTypeEn contractTypeEn) {
		this.contractTypeEn = contractTypeEn;
	}

	public ProjectPOJO getProject() {
		return project;
	}

	public void setProject(ProjectPOJO project) {
		this.project = project;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	public BuySellEn getBuySellEn() {
		return buySellEn;
	}

	public void setBuySellEn(BuySellEn buySellEn) {
		this.buySellEn = buySellEn;
	}

	public Set<ContractBillInfoPOJO> getContarctBillInfos() {
		return contarctBillInfos;
	}

	public void setContarctBillInfos(Set<ContractBillInfoPOJO> contarctBillInfos) {
		this.contarctBillInfos = contarctBillInfos;
	}

	public ContractSubCategoryPOJO getContractSubCategory() {
		return contractSubCategory;
	}

	public void setContractSubCategory(ContractSubCategoryPOJO contractSubCategory) {
		this.contractSubCategory = contractSubCategory;
	}

	public ContractCategoryPOJO getContractCategory() {
		return contractCategory;
	}

	public void setContractCategory(ContractCategoryPOJO contractCategory) {
		this.contractCategory = contractCategory;
	}	
}
