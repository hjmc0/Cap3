package com.uob.cap3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uob.cap3.entities.Transaction;
import java.util.List;


@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}
