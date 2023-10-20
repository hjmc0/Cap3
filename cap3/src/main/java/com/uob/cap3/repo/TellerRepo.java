package com.uob.cap3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uob.cap3.entities.Teller;

public interface TellerRepo extends JpaRepository<Teller,Long>{

    Teller findByTellerName(String tellerName);
    
}
