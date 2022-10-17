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

import com.komrz.trackxbackend.dto.LatestEventCalendarViewDTO;
import com.komrz.trackxbackend.dto.NewContractCalendarViewDTO;
import com.komrz.trackxbackend.dto.RenewalContractCalendarViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class CalendarViewRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<LatestEventCalendarViewDTO> latestEvent(String tenantId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		String sql = "SELECT ev.event_id, ev.event_title, ev.event_type, ct.contract_name, " + 
					"	to_char(end_time, 'DD-Mon-YYYY') end_date, SUM(bi.bill_amnt_pref_curr) amount, ct.buy_sell " + 
					"FROM trackx.event ev, trackx.contract ct, trackx.contract_bill_info bi " + 
					"WHERE ev.contract_id = ct.contract_id AND ct.contract_id = bi.contract_id " + 
					"	AND ct.tenant_id = :tenantId " + 
					"GROUP BY ev.event_id, ev.event_title, ev.event_type, ct.contract_name, to_char(end_time, 'DD-Mon-YYYY'), ct.buy_sell " + 
					"ORDER BY end_time desc fetch first 10 rows only;";
		
		return (List<LatestEventCalendarViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<LatestEventCalendarViewDTO>() {
					public LatestEventCalendarViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						LatestEventCalendarViewDTO eve = new LatestEventCalendarViewDTO();
						eve.setEventId(rs.getString(1));
						eve.setEventName(rs.getString(2));
						eve.setEventType(rs.getString(3));
						eve.setContractName(rs.getString(4));
						eve.setEndDate(rs.getString(5));
						eve.setAmount(rs.getString(6));						
						eve.setBuySell(rs.getString(7));						
						return eve;
					}
				});
	}
	
	//newContract with spend revenue
	public List<NewContractCalendarViewDTO> newContract(String tenantId, BuySellEn buySell) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT ct.contract_name, vn.vendor_name, to_char(contract_start_date, 'DD-Mon-YYYY') start_date, " + 
					"	SUM(bi.bill_amnt_pref_curr) amount " + 
					"FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.vendor vn " + 
					"WHERE ct.contract_id = bi.contract_id " + 
					"	AND ct.tenant_id = :tenantId " + 
					"	AND ct.vendor_id = vn.vendor_id " + 
					"	AND ct.status = 'active' " + 
					"	AND ct.buy_sell = :buySell " + 
					"GROUP BY ct.contract_name, vn.vendor_name, to_char(contract_start_date, 'DD-Mon-YYYY') " + 
					"ORDER BY start_date desc fetch first 10 rows only;";
		
		return (List<NewContractCalendarViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<NewContractCalendarViewDTO>() {
					public NewContractCalendarViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						NewContractCalendarViewDTO con = new NewContractCalendarViewDTO();
						con.setContractName(rs.getString(1));
						con.setVendorName(rs.getString(2));
						con.setStartDate(rs.getString(3));
						con.setAmount(rs.getString(4));						
						return con;
					}
				});
	}
	
	//renewalContract with spend revenue
	public List<RenewalContractCalendarViewDTO> renewalContract(String tenantId, BuySellEn buySell) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT ct.contract_name, vn.vendor_name, to_char(contract_renewal_date, 'DD-Mon-YYYY') renewal_date, " + 
					"	SUM(bi.bill_amnt_pref_curr) amount " + 
					"FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.vendor vn " + 
					"WHERE ct.contract_id = bi.contract_id " + 
					"	AND ct.tenant_id = :tenantId " + 
					"	AND ct.vendor_id = vn.vendor_id " + 
					"	AND ct.status = 'active' " + 
					"	AND ct.buy_sell = :buySell " + 
					"GROUP BY ct.contract_name, vn.vendor_name, to_char(contract_renewal_date, 'DD-Mon-YYYY') " + 
					"ORDER BY renewal_date desc fetch first 10 rows only;";
		
		return (List<RenewalContractCalendarViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<RenewalContractCalendarViewDTO>() {
					public RenewalContractCalendarViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						RenewalContractCalendarViewDTO con = new RenewalContractCalendarViewDTO();
						con.setContractName(rs.getString(1));
						con.setVendorName(rs.getString(2));
						con.setRenewalDate(rs.getString(3));
						con.setAmount(rs.getString(4));						
						return con;
					}
				});
	}
}
