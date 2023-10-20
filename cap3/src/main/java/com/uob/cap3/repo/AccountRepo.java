package com.uob.cap3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uob.cap3.entities.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long>{
    
    @Query(value = "select balance from account where account_id = :aid", nativeQuery = true)
    double findbalance(long aid);
}
