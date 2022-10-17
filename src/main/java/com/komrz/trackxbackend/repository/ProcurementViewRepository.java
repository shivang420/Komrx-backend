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

import com.komrz.trackxbackend.dto.ActiveContractEventProcurementView;
import com.komrz.trackxbackend.dto.ContractExpiryProcurementViewDTO;
import com.komrz.trackxbackend.dto.TopVendorProcurementViewDTO;
import com.komrz.trackxbackend.dto.VendorFetchDTO;
import com.komrz.trackxbackend.dto.VendorStatusProcurementViewDTO;
import com.komrz.trackxbackend.dto.VendorTypeProcurementViewDTO;
import com.komrz.trackxbackend.dto.YoyComparisonProcurementViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class ProcurementViewRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//VendorStatus
	public List<VendorStatusProcurementViewDTO> vendorStatus(String tenantId, BuySellEn buySell){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT vn.status, COUNT(distinct vn.vendor_id) " + 
					"FROM trackx.vendor vn, trackx.contract ct " + 
					"WHERE vn.vendor_id = ct.vendor_id " + 
					"	AND ct.buy_sell = :buySell " + 
					"	AND ct.tenant_id = :tenantId " + 
					"GROUP BY vn.status;";
		
		
		return (List<VendorStatusProcurementViewDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<VendorStatusProcurementViewDTO>() {
					public VendorStatusProcurementViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			            VendorStatusProcurementViewDTO ven = new VendorStatusProcurementViewDTO();
						ven.setVendorStatus(rs.getString(1));
						ven.setCount(rs.getInt(2));
			            return ven;
			        }
				});
		
	}
	
	//VendorType
		public List<VendorTypeProcurementViewDTO> vendorType(String tenantId, BuySellEn buySell){
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("tenantId", tenantId);
			parameters.addValue("buySell", buySell);
			parameters.registerSqlType("buySell", Types.OTHER);
			String sql = "SELECT vn.vendor_type, count(distinct vn.vendor_id) "
					+ "FROM trackx.vendor vn , trackx.contract ct " + 
						"WHERE vn.vendor_id = ct.vendor_id " + 
							"AND ct.buy_sell = :buySell " + 
							"AND ct.tenant_id = :tenantId " + 
						"GROUP BY vn.vendor_type; " ;
			
			return (List<VendorTypeProcurementViewDTO>)jdbcTemplate.query(
					sql, 
					parameters, 
					new RowMapper<VendorTypeProcurementViewDTO>() {
						public VendorTypeProcurementViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							VendorTypeProcurementViewDTO ven = new VendorTypeProcurementViewDTO();
							ven.setVendorType(rs.getString(1));
							ven.setCount(rs.getInt(2));
				            return ven;
				        }
					});
			
		}
	
	//YOY Comparison
	public List<YoyComparisonProcurementViewDTO> yoyComparison(String tenantId, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("year", year);
		String sql = "SELECT ct.buy_sell, SUM(bi.bill_amnt_pref_curr) " + 
				"FROM trackx.contract ct, trackx.contract_bill_info bi " + 
				"WHERE ct.contract_id = bi.contract_id " + 
				"	AND to_char(bill_date,'YYYY') = :year " + 
				"	AND ct.tenant_id = :tenantId " + 
				"GROUP BY ct.buy_sell;";
		
		return (List<YoyComparisonProcurementViewDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<YoyComparisonProcurementViewDTO>() {
					public YoyComparisonProcurementViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						YoyComparisonProcurementViewDTO yoy = new YoyComparisonProcurementViewDTO();
						yoy.setBuySell(rs.getString(1));
						yoy.setAmount(rs.getString(2));
						return yoy;
			        }
				});
	}
	
	//
	public List<TopVendorProcurementViewDTO> topVendor(String tenantId, BuySellEn buySell, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("year", year);
		parameters.addValue("buySell", buySell);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT vn.vendor_name,  vn.country " + 
					"FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.vendor vn " + 
					"WHERE ct.contract_id = bi.contract_id " + 
					"	AND ct.vendor_id = vn.vendor_id " + 
					"	AND ct.buy_sell = :buySell " + 
					"	AND to_char(bill_date,'YYYY') = :year " + 
					"	AND ct.tenant_id = :tenantId " + 
					"GROUP BY vn.vendor_name,vn.country " + 
					"ORDER BY SUM(bi.bill_amnt_pref_curr) DESC FETCH FIRST 10 ROWS ONLY;";
		
		return (List<TopVendorProcurementViewDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<TopVendorProcurementViewDTO>() {
					public TopVendorProcurementViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						TopVendorProcurementViewDTO top = new TopVendorProcurementViewDTO();
						top.setVendorName(rs.getString(1));
						top.setCountry(rs.getString(2));
						return top;
			        }
				});		
	}
	
	public List<VendorFetchDTO> topVendorForCategory(String tenantId, BuySellEn buySell, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("year", year);
		parameters.addValue("buySell", buySell);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT vn.vendor_name,  vn.vendor_id, vn.payment_terms, vn.currency  " + 
					"FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.vendor vn " + 
					"WHERE ct.contract_id = bi.contract_id " + 
					"	AND ct.vendor_id = vn.vendor_id " + 
					"	AND ct.buy_sell = :buySell " + 
					"	AND to_char(bill_date,'YYYY') = :year " + 
					"	AND ct.tenant_id = :tenantId " + 
					"GROUP BY vn.vendor_name,vn.vendor_id, vn.payment_terms, vn.currency  " + 
					"ORDER BY SUM(bi.bill_amnt_pref_curr) DESC FETCH FIRST 10 ROWS ONLY;";
		
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
	
	//ContractExpiry
	public List<ContractExpiryProcurementViewDTO> contractExpiry(String tenantId, int startPeriod, int endPeriod){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("startPeriod", startPeriod);
		parameters.addValue("endPeriod", endPeriod);
		String sql = "SELECT ct.contract_name, ct.contract_type, ct.contract_end_date " + 
					"FROM trackx.contract ct " + 
					"WHERE ct.status = 'active' AND ct.tenant_id = :tenantId " + 
					"	AND ct.contract_end_date between current_date + :startPeriod and current_date + :endPeriod " + 
					"ORDER BY ct.contract_end_date;";
		
		return (List<ContractExpiryProcurementViewDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<ContractExpiryProcurementViewDTO>() {
					public ContractExpiryProcurementViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						ContractExpiryProcurementViewDTO con = new ContractExpiryProcurementViewDTO();
						con.setContractName(rs.getString(1));
						con.setContractType(rs.getString(2));
						con.setEndDate(rs.getDate(3));
						return con;
			        }
				});		
	}
	
	//ActiveContract With No Events
	public List<ActiveContractEventProcurementView> activeContractEvent(String tenantId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		String sql = "SELECT ct.contract_name, ct.contract_type, ct.contract_end_date " + 
					"FROM trackx.contract ct " + 
					"LEFT JOIN  trackx.event ev ON ct.contract_id = ev.contract_id " + 
					"WHERE ct.status ='active' " + 
					"AND ct.tenant_id = :tenantId " + 
					"GROUP BY ct.contract_name, ct.contract_type, ct.contract_end_date having count(ev.event_id) = 0 " + 
					"ORDER BY ct.contract_end_date;";
		
		return (List<ActiveContractEventProcurementView>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<ActiveContractEventProcurementView>() {
					public ActiveContractEventProcurementView mapRow(ResultSet rs, int rowNum) throws SQLException {
						ActiveContractEventProcurementView con = new ActiveContractEventProcurementView();
						con.setContractName(rs.getString(1));
						con.setContractType(rs.getString(2));
						con.setEndDate(rs.getDate(3));
						return con;
			        }
				});		
	}
	
	//Vendor Wise Category
	public Double vendorWiseCategory(String tenantId, BuySellEn buySell, String year, String vendorId, String contractCategoryId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("year", year);
		parameters.addValue("buySell", buySell);
		parameters.addValue("vendorId", vendorId);
		parameters.addValue("categoryId", contractCategoryId);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT  SUM(bi.bill_amnt_pref_curr) " + 
					"FROM trackx.contract ct, trackx.contract_bill_info bi " + 
					"WHERE ct.contract_id = bi.contract_id " + 
					"	AND ct.vendor_id = :vendorId " + 
					"	AND ct.contract_category_id = :categoryId " + 
					"	AND ct.buy_sell = :buySell " + 
					"	AND to_char(bill_date,'YYYY') = :year " + 
					"	AND ct.tenant_id = :tenantId ";
		
		return (Double)jdbcTemplate.queryForObject(
				sql, 
				parameters, 
				new RowMapper<Double>() {
					public Double mapRow(ResultSet rs, int rowNum) throws SQLException {
						return rs.getDouble(1);
			        }
				});		
	}
}
