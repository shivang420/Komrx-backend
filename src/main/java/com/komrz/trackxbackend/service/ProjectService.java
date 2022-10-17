package com.komrz.trackxbackend.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.komrz.trackxbackend.dto.ProjectCreateDTO;
import com.komrz.trackxbackend.enumerator.PppStatus;
import com.komrz.trackxbackend.model.ProjectPOJO;
import com.komrz.trackxbackend.repository.ProgramRepository;
import com.komrz.trackxbackend.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProgramRepository programRepository;
	
	public String createProject(ProjectCreateDTO projectCreateDTO) {
		ProjectPOJO newProject = new ProjectPOJO();
		newProject.setProjectName(projectCreateDTO.getProjectName());
		newProject.setProgram(programRepository.getOne(projectCreateDTO.getProgramId()));
		newProject.setStatus(PppStatus.active);
		return projectRepository.save(newProject).getId();	
	}

	// return true if exists
	// return false if not exists
	public boolean isExist(String projectId, String tenantId) {
		ProjectPOJO project = projectRepository.findByProjectId(projectId);
		if(project == null) {
			return false;
		}
		if(tenantId.equals(project.getProgram().getPortfolio().getTenantId())) {
			return true;
		}
		return false;
	}
	
	// return true if exists
	// return false if not exists
	public boolean isExist(ProjectCreateDTO projectCreateDTO, String tenantId) {
		List<ProjectPOJO> project = projectRepository.findByProjectName(projectCreateDTO.getProjectName());
		if (project.isEmpty()) {
			return false;
		}
		ListIterator<ProjectPOJO> listIterator = project.listIterator();
		while (listIterator.hasNext()) {
			if(tenantId.equals(listIterator.next().getProgram().getPortfolio().getTenantId())) {
				return true;
			}
		}
		return false;
	}
	
	public String updateProject(String projectId, ProjectCreateDTO projectCreateDTO) {
		
		ProjectPOJO projectPOJO = projectRepository.getOne(projectId);
		projectPOJO.setProjectName(projectCreateDTO.getProjectName());
		return projectRepository.save(projectPOJO).getId();		
	}

	public List<ProjectPOJO> getAllProject() {
		return projectRepository.findAll();
	}
	
	public List<ProjectPOJO> getAllProject(String programId) {
		return projectRepository.findByProgramId(programId);
	}
	
	//public Optional<ProjectPOJO> fetchProjectId(String projectId) {	
		//return projectRepository.findById(projectId);
	//}
	
	public ProjectPOJO fetchProjectId(String projectId) {	
		return projectRepository.findByProjectId(projectId);
	}
}