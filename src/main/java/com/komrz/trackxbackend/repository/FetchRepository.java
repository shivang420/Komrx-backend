package com.komrz.trackxbackend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.komrz.trackxbackend.dto.ContractFetchDTO;
import com.komrz.trackxbackend.dto.VendorFetchDTO;

@Component
public class FetchRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<VendorFetchDTO> getVendor(String tenantId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		String sql = "SELECT vn.vendor_name,  vn.vendor_id, vn.payment_terms, vn.currency " + 
					"FROM  trackx.vendor vn " + 
					"WHERE vn.tenant_id = :tenantId " + 
					"GROUP BY vn.vendor_name,vn.vendor_id, vn.payment_terms, vn.currency " ;		
		return (List<VendorFetchDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<VendorFetchDTO>() {
					public VendorFetchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						VendorFetchDTO top = new VendorFetchDTO();
						top.setVendorName(rs.getString(1));
						top.setVendorId(rs.getString(2));
						top.setPaymentTerms(rs.getString(3));
						top.setCurrency(rs.getString(4));
						return top;
			        }
				});		
	}
	
	public List<ContractFetchDTO> getContract(String tenantId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		String sql = "SELECT cc.contract_id,  cc.contract_name " + 
					"FROM  trackx.contract cc " + 
					"WHERE cc.tenant_id = :tenantId " + 
					"GROUP BY cc.contract_name,cc.contract_id " ;		
		return (List<ContractFetchDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<ContractFetchDTO>() {
					public ContractFetchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						ContractFetchDTO top = new ContractFetchDTO();
						top.setContractId(rs.getString(1));
						top.setContractName(rs.getString(2));
						return top;
			        }
				});		
	}
}
