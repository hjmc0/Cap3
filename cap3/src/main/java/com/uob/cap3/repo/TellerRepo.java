package com.uob.cap3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uob.cap3.entities.Teller;

@Repository
public interface TellerRepo extends JpaRepository<Teller,Long>{

    Teller findByTellerName(String tellerName);
    
}
