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

import com.komrz.trackxbackend.dto.PortfolioViewDTO;
import com.komrz.trackxbackend.enumerator.BuySellEn;

/**
 * @author souravbhattacharjee
 * @author shivang gupta
 *
 */
@Component
public class PortfolioViewRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	//Top Portfolio
	public List<PortfolioViewDTO> topPortfolio(String tenantId, BuySellEn buySell, String startDate, String endDate){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("tenantId", tenantId);
		parameters.addValue("buySell", buySell);
		parameters.addValue("startDate", startDate);
		parameters.addValue("endDate", endDate);
		parameters.registerSqlType("startDate", Types.DATE);
		parameters.registerSqlType("endDate", Types.DATE);
		parameters.registerSqlType("buySell", Types.OTHER);
		String sql = "SELECT pf.portfolio_id, pf.portfolio_name, pg.program_name, cc.cost_center_name, pj.project_name, " + 
					"	SUM (bi.bill_amnt_pref_curr) total_amount " + 
					"FROM trackx.contract ct, trackx.portfolio pf, trackx.cost_center cc, trackx.project pj, " + 
					"	trackx.program pg, trackx.contract_bill_info bi " + 
					"WHERE ct.project_id = pj.project_id " + 
					"	AND pj.program_id=pg.program_id " + 
					"	AND pg.portfolio_id = pf.portfolio_id " + 
					"	AND ct.contract_id = bi.contract_id " + 
					"	AND ct.cost_center_id = cc.cost_center_id " + 
					"	AND ct.tenant_id = :tenantId " + 
					"	AND ct.buy_sell = :buySell " + 
					"	AND bi.bill_date BETWEEN :startDate AND :endDate " + 
					"GROUP BY pf.portfolio_id, pf.portfolio_name, pg.program_name, cc.cost_center_name, " + 
					"	pj.project_name " + 
					"ORDER BY pf.portfolio_name, pg.program_name, cc.cost_center_name, pj.project_name;";
		
		return (List<PortfolioViewDTO>)jdbcTemplate.query(
				sql,
				parameters,
				new RowMapper<PortfolioViewDTO>() {
					public PortfolioViewDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
						PortfolioViewDTO port = new PortfolioViewDTO();
						port.setPortfolioId(rs.getString(1));
						port.setPortfolioName(rs.getString(2));
						port.setProgramName(rs.getString(3));
						port.setCostCenterName(rs.getString(4));
						port.setProjectName(rs.getString(5));
						port.setTotalAmount(rs.getString(6));
						return port;
					}
				});
	}
}
