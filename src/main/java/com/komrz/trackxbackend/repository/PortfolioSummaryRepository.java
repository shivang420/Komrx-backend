package com.komrz.trackxbackend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.komrz.trackxbackend.dto.ProgramInfoPortfolioSummaryDTO;
import com.komrz.trackxbackend.dto.ProjectInfoPortfolioSummaryDTO;

@Component
public class PortfolioSummaryRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public List<ProgramInfoPortfolioSummaryDTO> revenueSpend(String portfolioId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("portfolioId", portfolioId);
		String sql = "SELECT pg.program_name,ct.buy_sell, SUM (bi.bill_amnt_pref_curr) bill_amount " + 
				"FROM trackx.portfolio pf, trackx.program pg, " + 
				"	trackx.project pj, trackx.contract ct, trackx.contract_bill_info bi " + 
				"WHERE pf.portfolio_id = pg.portfolio_id " + 
				"	AND pg.program_id = pj.program_id " + 
				"	AND pj.project_id = ct.project_id " + 
				"	AND pf.portfolio_id = :portfolioId " + 
				"GROUP BY pg.program_name, ct.buy_sell " + 
				"ORDER BY pg.program_name, ct.buy_sell;";
		
		return (List<ProgramInfoPortfolioSummaryDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<ProgramInfoPortfolioSummaryDTO>() {
					public ProgramInfoPortfolioSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						ProgramInfoPortfolioSummaryDTO revenueSpend = new ProgramInfoPortfolioSummaryDTO();
						revenueSpend.setProgramName(rs.getString(1));
						revenueSpend.setBuySell(rs.getString(2));
						revenueSpend.setAmount(rs.getDouble(3));
						return revenueSpend;
					}
				});
	}
	
	public List<ProjectInfoPortfolioSummaryDTO> projectInfo(String portfolioId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("portfolioId", portfolioId);
		String sql = "SELECT rs.program_name, rs.project_name,  sum (rs.spent_amount) spent_amount , sum(rs.revenue_amount) revenue_amount " + 
				"FROM ( " + 
				"	SELECT pg.program_name, pj.project_name, sum (bi.bill_amnt_pref_curr) spent_amount, 0 revenue_amount " + 
				"	FROM trackx.portfolio pf, trackx.program pg, " + 
				"		trackx.project pj, trackx.contract ct, trackx.contract_bill_info bi " + 
				"	WHERE pf.portfolio_id = pg.portfolio_id " + 
				"		AND pg.program_id = pj.program_id " + 
				"		AND pj.project_id = ct.project_id " + 
				"		AND ct.contract_id = bi.contract_id " + 
				"		AND pf.portfolio_id = :portfolioId " + 
				"		AND ct.buy_sell = 'buy' " + 
				"	GROUP BY  pg.program_name, pj.project_name,  revenue_amount " + 
				"	UNION " + 
				"	SELECT pg.program_name, pj.project_name, 0 spent_amount,  sum (bi.bill_amnt_pref_curr) revenue_amount " + 
				"	FROM trackx.portfolio pf, trackx.program pg, " + 
				"		trackx.project pj, trackx.contract ct, trackx.contract_bill_info bi " + 
				"	WHERE pf.portfolio_id = pg.portfolio_id " + 
				"		AND pg.program_id = pj.program_id " + 
				"		AND pj.project_id = ct.project_id " + 
				"		AND ct.contract_id = bi.contract_id " + 
				"		AND pf.portfolio_id = :portfolioId " + 
				"		AND ct.buy_sell = 'sell' " + 
				"	GROUP BY  pg.program_name, pj.project_name,  spent_amount)  rs " + 
				"GROUP BY rs.program_name, rs.project_name " + 
				"ORDER BY rs.program_name, rs.project_name , spent_amount, revenue_amount;";
		
		return (List<ProjectInfoPortfolioSummaryDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<ProjectInfoPortfolioSummaryDTO>() {
					public ProjectInfoPortfolioSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						ProjectInfoPortfolioSummaryDTO projectInfo = new ProjectInfoPortfolioSummaryDTO();
						projectInfo.setProgramName(rs.getString(1));
						projectInfo.setProjectName(rs.getString(2));
						projectInfo.setSpendAmount(rs.getDouble(3));
						projectInfo.setRevenueAmount(rs.getDouble(4));
						return projectInfo;
					}
				});
	}
}
