package com.komrz.trackxbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.UserInvitePOJO;

@Repository
public interface UserInviteRepository extends JpaRepository<UserInvitePOJO, String> {

	@Query("SELECT user FROM UserInvitePOJO user WHERE user.userInviteId = ?1")
	UserInvitePOJO findByUserInviteId(String userInviteId);

	UserInvitePOJO findByEmail(String username);
}
