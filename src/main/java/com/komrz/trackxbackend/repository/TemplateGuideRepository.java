package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.TemplateGuidesPOJO;

@Repository
public interface TemplateGuideRepository extends JpaRepository<TemplateGuidesPOJO, String> {

	List<TemplateGuidesPOJO> findByTenantId(String string);

}
