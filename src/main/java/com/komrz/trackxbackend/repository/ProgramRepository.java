package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.ProgramPOJO;

@Repository
public interface ProgramRepository extends JpaRepository<ProgramPOJO, String> {

	List<ProgramPOJO> findByPortfolioId(String portfolioId);
	
	@Query("SELECT pro.id FROM ProgramPOJO pro WHERE pro.programName = ?1 AND pro.portfolio.id = ?2")
	String isExist(String programName, String portfolioId);
	
	@Query("SELECT pro FROM ProgramPOJO pro WHERE pro.id = ?1")
	ProgramPOJO findByProgramId(String programId);

	List<ProgramPOJO> findByProgramName(String programName);
}
