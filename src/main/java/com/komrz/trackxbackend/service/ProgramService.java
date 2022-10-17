package com.komrz.trackxbackend.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ProgramCreateDTO;
import com.komrz.trackxbackend.enumerator.PppStatus;
import com.komrz.trackxbackend.model.ProgramPOJO;
import com.komrz.trackxbackend.repository.PortfolioRepository;
import com.komrz.trackxbackend.repository.ProgramRepository;

@Service
public class ProgramService {
	
	@Autowired
	private ProgramRepository programRepository;
	
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	public String createProgram(ProgramCreateDTO programCreateDTO) {
		ProgramPOJO newProgram = new ProgramPOJO();
		newProgram.setProgramName(programCreateDTO.getProgramName());
		newProgram.setPortfolio(portfolioRepository.getOne(programCreateDTO.getPortfolioId()));
//		newProgram.setPortfolioId(programCreateDTO.getPortfolioId());
		newProgram.setStatus(PppStatus.active);
		return programRepository.save(newProgram).getId();	
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String programId, String tenantId) {
		ProgramPOJO program = programRepository.findByProgramId(programId);
		if(program == null) {
			return false;
		}
		if(tenantId.equals(program.getPortfolio().getTenantId())) {
			return true;
		}
		return false;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(ProgramCreateDTO programCreateDTO, String tenantId) {
		List<ProgramPOJO> program = programRepository.findByProgramName(programCreateDTO.getProgramName());
		if (program.isEmpty()) {
			return false;
		}
		ListIterator<ProgramPOJO> listIterator = program.listIterator();
		while (listIterator.hasNext()) {
			if(tenantId.equals(listIterator.next().getPortfolio().getTenantId())) {
				return true;
			}
		}
		return false;
	}
	
	public String updateProgram(String programId, ProgramCreateDTO programCreateDTO) {
		
		ProgramPOJO programPOJO = programRepository.getOne(programId);
		programPOJO.setProgramName(programCreateDTO.getProgramName());
		return programRepository.save(programPOJO).getId();		
	}

	public List<ProgramPOJO> getAllProgram() {
		return programRepository.findAll();
	}
	
	public List<ProgramPOJO> getAllProgram(String portfolioId) {
		return programRepository.findByPortfolioId(portfolioId);
	}

	public ProgramPOJO fetchProgramId(String programId) {	
		return programRepository.findByProgramId(programId);
	}
}