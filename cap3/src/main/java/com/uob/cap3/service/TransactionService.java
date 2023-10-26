package com.uob.cap3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uob.cap3.entities.Transaction;
import com.uob.cap3.repo.TransactionRepo;

@Service
public class TransactionService {
    @Autowired
    TransactionRepo tr;

    public List<Transaction> searchTransaction(Long id, String query) {
        List<Transaction> transactions = tr.findByAccountId(id);
        String lowercaseQuery = query.toLowerCase();
        return transactions.stream()
                .filter(transaction -> (String.valueOf(transaction.getTransId())).contains(lowercaseQuery)
                        || (String.valueOf(transaction.getAmount())).contains(lowercaseQuery)
                        || transaction.getTransType().toLowerCase().contains(lowercaseQuery)
                        || (String.valueOf(transaction.getTransDate())).contains(lowercaseQuery))
                .collect(Collectors.toList());
    }
}
