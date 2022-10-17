package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.ProjectPOJO;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectPOJO, String> {

	List<ProjectPOJO> findByProgramId(String programId);
	
	@Query("SELECT pj.id FROM ProjectPOJO pj WHERE pj.projectName = ?1 AND pj.program.id = ?2")
	String isExist(String projectName, String programId);
	
	@Query("SELECT pj FROM ProjectPOJO pj WHERE pj.id = ?1")
	ProjectPOJO findByProjectId(String projectId);

	List<ProjectPOJO> findByProjectName(String projectName);
}
