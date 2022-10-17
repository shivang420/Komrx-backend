package com.komrz.trackxbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

	List<User> findByTenantId(String tenantId);
	
	@Query("SELECT user FROM User user WHERE user.id = ?1")
	User findByUserId(String userId);
	
	@Query("SELECT user.id FROM User user WHERE user.username = ?1")
	String isExist(String username);
	
	List<User> findByIdAndTenantId(String userId, String tenantId);
}
