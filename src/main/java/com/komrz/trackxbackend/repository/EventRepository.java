package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.EventPOJO;

@Repository
public interface EventRepository extends JpaRepository<EventPOJO, String> {

	List<EventPOJO> findByContractId(String contractId);
	
	@Query("SELECT eve FROM EventPOJO eve WHERE eve.id = ?1")
	EventPOJO findByEventId(String eventId);
	
	@Query("SELECT eve.id FROM EventPOJO eve WHERE eve.eventTitle = ?1 AND eve.contractId = ?2")
	String isExist(String eventTitle, String contractId);
}
