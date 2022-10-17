package com.komrz.trackxbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.komrz.trackxbackend.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,String> {

}
