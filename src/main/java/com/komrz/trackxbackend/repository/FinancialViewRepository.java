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

import com.komrz.trackxbackend.dto.CategoryFinancialViewDTO;
import com.komrz.trackxbackend.dto.CostCenterFinancialViewDTO;
import com.komrz.trackxbackend.dto.FinancialTrendFinancialViewFetchDTO;
import com.komrz.trackxbackend.dto.ProgramFinancialViewDTO;
import com.komrz.trackxbackend.dto.TopContractFinancialViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class FinancialViewRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//Financial Trend
	public List<FinancialTrendFinancialViewFetchDTO> financialTrendFinancialView(String tenantId, String year) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("year", year);
		String sql = "SELECT ct.buy_sell AS buySell, to_char(bi.bill_date,'mon') AS billMonth, SUM(bi.bill_amnt_pref_curr) AS billAmount " + 
				"FROM trackx.contract ct, trackx.contract_bill_info bi " + 
				"WHERE ct.contract_id=bi.contract_id AND ct.status = 'active' " + 
				"		AND ct.tenant_id = :tenantId " +
				"		AND to_char(bill_date,'YYYY') = :year " + 
				"GROUP BY ct.buy_sell, billMonth;";
		
		return (List<FinancialTrendFinancialViewFetchDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<FinancialTrendFinancialViewFetchDTO>() {
					public FinancialTrendFinancialViewFetchDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						FinancialTrendFinancialViewFetchDTO fin = new FinancialTrendFinancialViewFetchDTO();
						fin.setBuySell(rs.getString("buySell"));
						fin.setBillMonth(rs.getString("billMonth"));
						fin.setBillAmount(rs.getString("billAmount"));
			            return fin;
			        }
				});
	}
	
	//Top 10 Contracts
	public List<TopContractFinancialViewDTO> topContractFinancialView(String tenantId, BuySellEn buySell, String startDate, String endDate){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("startDate", startDate);
		parameters.addValue("endDate", endDate);
		parameters.registerSqlType("startDate", Types.DATE);
		parameters.registerSqlType("endDate", Types.DATE);
		parameters.registerSqlType("buySell", Types.OTHER);
		
		String sql = "SELECT rs.contract_id AS contractId, rs.contract_name AS contractName, rs.vendor_name AS vendorName," + 
				" rs.contract_start_date AS startDate, rs.contract_end_date AS endDate, rs.amount AS amount, SUM(bi.bill_amnt_pref_curr) AS tcv " + 
				"FROM " + 
				"		(SELECT ct.contract_id, ct.contract_name, vn.vendor_name, ct.buy_sell, ct.contract_start_date, " + 
				"				ct.contract_end_date, SUM(bi.bill_amnt_pref_curr) amount " + 
				"		FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.vendor vn " + 
				"		WHERE ct.contract_id=bi.contract_id AND ct.vendor_id = vn.vendor_id " + 
				"				AND ct.status = 'active' AND vn.status = 'active' AND ct.buy_sell = :buySell " + 
				"				AND ct.tenant_id = :tenantId " + 
				"				AND bi.bill_date BETWEEN :startDate AND :endDate " + 
				"		GROUP BY ct.contract_id, ct.contract_name,vn.vendor_name, ct.buy_sell " + 
				"		ORDER BY amount FETCH FIRST 10 ROWS ONLY) rs, " + 
				"	trackx.contract_bill_info bi " + 
				"GROUP BY rs.contract_id, rs.contract_name, rs.vendor_name, rs.buy_sell, rs.contract_start_date, " + 
				"			rs.contract_end_date, rs.amount " + 
				"ORDER BY rs.amount DESC;";
		
		return (List<TopContractFinancialViewDTO>)jdbcTemplate.query(
				sql, 
				parameters, 
				new RowMapper<TopContractFinancialViewDTO>() {
					public TopContractFinancialViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						TopContractFinancialViewDTO top = new TopContractFinancialViewDTO();
						top.setContractId(rs.getString("contractId"));
						top.setContractName(rs.getString("contractName"));
						top.setVendorName(rs.getString("vendorName"));
						top.setStartDate(rs.getDate("startDate"));
						top.setEndDate(rs.getDate("endDate"));
						top.setAmount(rs.getString("amount"));
						top.setTcv(rs.getString("tcv"));
			            return top;
			        }
				});
	}	

	//Spend/Revenue by Category
	public List<CategoryFinancialViewDTO> categoryFinancailView(String tenantId, BuySellEn buySell, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("year", year);
		parameters.registerSqlType("buySell", Types.OTHER);
		
		String sql = "SELECT cc.contract_category_name AS contractCategoryName,to_char(bi.bill_date, 'Q') AS quarter, "
				+ "SUM(bi.bill_amnt_pref_curr) AS amount " + 
				"FROM trackx.contract ct, trackx.contract_category cc, trackx.contract_bill_info bi " + 
				"WHERE ct.contract_category_id = cc.contract_category_id " + 
				"	AND ct.contract_id=bi.contract_id " + 
				"	AND ct.status = 'active' " + 
				"	AND ct.buy_sell = :buySell " + 
				"	AND ct.tenant_id = :tenantId " + 
				"	AND to_char(bill_date,'YYYY') = :year " + 
				"GROUP BY cc.contract_category_name, to_char(bi.bill_date, 'Q');";
		return (List<CategoryFinancialViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<CategoryFinancialViewDTO>() {
					public CategoryFinancialViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						CategoryFinancialViewDTO cat = new CategoryFinancialViewDTO();
						cat.setContractCategoryName(rs.getString("contractCategoryName"));
						cat.setBillQuarter(rs.getString("quarter"));
						cat.setAmount(rs.getString("amount"));
						return cat;
					}
				});
	}

	//Spend/Revenue by Program
	public List<ProgramFinancialViewDTO> programFinancialView(String tenantId, String portfolioId, BuySellEn buySell, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("portfolioId", portfolioId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("year", year);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT pg.program_name AS programName, SUM(bi.bill_amnt_pref_curr) AS amount " + 
				"FROM trackx.contract ct, trackx.portfolio pf, trackx.program pg, trackx.project pj, " + 
				"	trackx.contract_bill_info bi " + 
				"WHERE ct.project_id = pj.project_id " + 
				"	AND pj.program_id = pg.program_id " + 
				"	AND pg.portfolio_id = pf.portfolio_id " + 
				"	AND ct.contract_id = bi.contract_id " + 
				"	AND ct.status = 'active' " + 
				"	AND pf.portfolio_id = :portfolioId " + 
				"	AND ct.buy_sell = :buySell " + 
				"	AND ct.tenant_id = :tenantId " + 
				"	AND to_char(bill_date,'YYYY') = :year " + 
				"GROUP BY pg.program_name;";
		return (List<ProgramFinancialViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<ProgramFinancialViewDTO>() {
					public ProgramFinancialViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						ProgramFinancialViewDTO pro = new ProgramFinancialViewDTO();
						pro.setProgramName(rs.getString("programName"));
						pro.setAmount(rs.getString("amount"));
						return pro;
					}
				});
	}

	//Spend/Revenue by Cost Center
	public List<CostCenterFinancialViewDTO> costCenterFinancialView(String tenantId, String legalEntityId, BuySellEn buySell, String year){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("legalEntityId", legalEntityId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("year", year);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql ="SELECT cc.cost_center_name AS costCenterName, sum(bi.bill_amnt_pref_curr) AS amount " + 
				"FROM trackx.contract ct, trackx.contract_bill_info bi, trackx.cost_center cc, " + 
				"	trackx.legal_entity le " + 
				"WHERE ct.contract_id=bi.contract_id " + 
				"	AND ct.cost_center_id = cc.cost_center_id " + 
				"	AND cc.legal_entity_id = le.legal_entity_id " + 
				"	AND ct.status = 'active' " + 
				"	AND ct.buy_sell = :buySell " + 
				"	AND ct.tenant_id = :tenantId " + 
				"	AND cc.legal_entity_id = :legalEntityId " + 
				"	AND to_char(bill_date,'YYYY') = :year " + 
				"GROUP BY cc.cost_center_name;";
		return (List<CostCenterFinancialViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<CostCenterFinancialViewDTO>() {
					public CostCenterFinancialViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						CostCenterFinancialViewDTO cc = new CostCenterFinancialViewDTO();
						cc.setCostCenterName(rs.getString("costCenterName"));
						cc.setAmount(rs.getString("amount"));
						return cc;
					}
				});
	}

	//Top KPI's
	public int totalContractFinancialView(String tenantId){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		String sql = "SELECT count(*) FROM trackx.contract WHERE status = 'active' and tenant_id = :tenantId ;";
		
		return jdbcTemplate.queryForObject(
				sql, 
				parameters, 
				new RowMapper<Integer>() {
					public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
						return rs.getInt(1);
					}
				});
	}
	
	//revenue and spend
	public String revenueAndSpendFinancialView(String tenantId, String buySell, String year) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("year", year);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "select sum (bi.bill_amnt_pref_curr) total_spent " + 
				"from trackx.contract ct, trackx.contract_bill_info bi " + 
				"where ct.contract_id = bi.contract_id " + 
				"and ct.status = 'active' " + 
				"and to_char(bill_date,'YYYY') = :year " + 
				"and ct.tenant_id = :tenantId " + 
				"and ct.buy_sell = :buySell;";
		
		return jdbcTemplate.queryForObject(
				sql, 
				parameters, 
				new RowMapper<String>() {
					public String mapRow(ResultSet rs, int rowNum) throws SQLException{
						return rs.getString(1);
					}
				});
	}
}
