package com.uob.cap3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uob.cap3.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {
    
}
