package com.komrz.trackxbackend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.komrz.trackxbackend.dto.BillInfoContractSummaryDTO;
import com.komrz.trackxbackend.dto.ContractSummaryDTO;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class ContractSummaryRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//contractSummary Meta Data
	public ContractSummaryDTO contractMetaData(String contractId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("contractId", contractId);
		String sql = "SELECT ct.contract_id, ct.contract_name, ct.notes, to_char(ct.contract_start_date, 'DD-Mon-YYYY'), " +
				"to_char(ct.contract_end_date, 'DD-Mon-YYYY'), to_char(ct.contract_renewal_date, 'DD-Mon-YYYY'), " + 
				" to_char(ct.contract_termination_date, 'DD-Mon-YYYY'), vn.vendor_name, vn.address, vn.vendor_contact_name, vn.phone, " + 
				"	rspj.project_name, rspj.program_name, rscc.cost_center_name, rscc.legal_entity_name " + 
				"FROM trackx.contract ct " + 
				"	LEFT JOIN trackx.vendor vn ON ct.vendor_id = vn.vendor_id " + 
				"	LEFT JOIN (	SELECT cc.cost_center_id, cc.cost_center_name, leg.legal_entity_name " + 
				"			   FROM trackx.cost_center cc LEFT JOIN trackx.legal_entity leg ON cc.legal_entity_id = leg.legal_entity_id " + 
				"			  ) rscc ON ct.cost_center_id = rscc.cost_center_id " + 
				"	LEFT JOIN ( SELECT pj.project_id, pj.project_name, pro.program_name " + 
				"			   FROM trackx.project pj LEFT JOIN trackx.program pro ON pj.program_id = pro.program_id " + 
				"			  ) rspj ON ct.project_id = rspj.project_id " + 
				"WHERE ct.contract_id = :contractId " + 
				"	AND ct.status = 'active'";
		
		return (ContractSummaryDTO)jdbcTemplate.queryForObject(
				sql, 
				parameters, 
				new RowMapper<ContractSummaryDTO>() {
					public ContractSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			            ContractSummaryDTO con = new ContractSummaryDTO();
						con.setContractId(rs.getString(1));
						con.setContractName(rs.getString(2));
						con.setNotes(rs.getString(3));
						con.setStartDate(rs.getString(4));
						con.setEndDate(rs.getString(5));
						con.setRenewalDate(rs.getString(6));
						con.setTerminationDate(rs.getString(7));
						con.setVendorName(rs.getString(8));
						con.setVendorAddress(rs.getString(9));
						con.setVendorContactName(rs.getString(10));
						con.setPhone(rs.getString(11));
						con.setProjectName(rs.getString(12));
						con.setProgramName(rs.getString(13));
						con.setCostCenterName(rs.getString(14));
						con.setLegalEntityName(rs.getString(15));
			            return con;
			        }
				});
	}

	public List<BillInfoContractSummaryDTO> billInfo(String contractId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("contractId", contractId);
		String sql = "SELECT to_char(bi.bill_date, 'DD-Mon-YYYY'), bi.bill_amnt_pref_curr " + 
				"FROM trackx.contract cc, trackx.contract_bill_info bi " + 
				"WHERE cc.contract_id = :contractId " + 
				"	AND cc.contract_id = bi.contract_id";
		
		return (List<BillInfoContractSummaryDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<BillInfoContractSummaryDTO>() {
					public BillInfoContractSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						BillInfoContractSummaryDTO bill = new BillInfoContractSummaryDTO();
						bill.setBillDate(rs.getString(1));
						bill.setAmount(rs.getString(2));
						return bill;
					}
				});
	}
}
