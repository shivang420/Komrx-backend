package com.komrz.trackxbackend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.komrz.trackxbackend.dto.AllContractViewDTO;
import com.komrz.trackxbackend.enumerator.ContractStatusEn;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class ContractViewRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<AllContractViewDTO> allContractView(String tenantId, ContractStatusEn contractStatus) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("contractStatus", contractStatus);
		parameters.registerSqlType("contractStatus", Types.OTHER);
		String sql = "SELECT ct.contract_id, ct.contract_name, ct.contract_type, ct.contract_start_date, " + 
					"		ct.contract_end_date, sum(bi.bill_amount) tcv, vn.vendor_name, ct.buy_sell " + 
					"FROM  trackx.contract ct " + 
					"	LEFT JOIN trackx.vendor vn ON ct.vendor_id = vn.vendor_id " + 
					"	LEFT JOIN trackx.contract_bill_info bi ON ct.contract_id = bi.contract_id " + 
					"WHERE ct.tenant_id = :tenantId AND ct.status = :contractStatus " + 
					"GROUP BY ct.contract_id, ct.contract_name, ct.contract_type , ct.contract_start_date, " + 
					"			ct.contract_end_date, vn.vendor_name, ct.buy_sell " + 
					"ORDER BY tcv DESC;";
		
		return (List<AllContractViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<AllContractViewDTO>() {
					public AllContractViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						AllContractViewDTO con = new AllContractViewDTO();
						con.setContractId(rs.getString(1));
						con.setContractName(rs.getString(2));
						con.setContractTypeEn(rs.getString(3));
						con.setStartDate(rs.getDate(4));
						con.setEndDate(rs.getDate(5));
						con.setTcv(rs.getString(6));
						con.setVendorName(rs.getString(7));
						con.setBuySellEn(rs.getString(8));
			            return con;
			        }
				});
	}
}
