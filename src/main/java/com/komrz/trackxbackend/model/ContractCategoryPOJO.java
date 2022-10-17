package com.komrz.trackxbackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "contract_category", schema = "trackx")
@JsonInclude(content = Include.NON_NULL)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContractCategoryPOJO {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "contract_category_id")
		private String id;
		
		@Column(name = "contract_category_name")
		private String contractCategoryName;
		
		@Column(name = "tenant_id")
		private String tenantId;

		//getters and setters
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContractCategoryName() {
			return contractCategoryName;
		}

		public void setContractCategoryName(String contractCategoryName) {
			this.contractCategoryName = contractCategoryName;
		}

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}
}
