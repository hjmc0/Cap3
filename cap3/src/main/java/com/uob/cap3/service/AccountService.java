package com.uob.cap3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uob.cap3.entities.Account;
import com.uob.cap3.repo.AccountRepo;

@Service
public class AccountService {
    @Autowired
    AccountRepo ar;

    public List<Account> searchAccounts(String query) {
        List<Account> accounts = new ArrayList<>();
        accounts = (List<Account>) ar.findAll();
        String lowercaseQuery = query.toLowerCase();
        return accounts.stream().filter(account -> (String.valueOf(account.getAccountId())).contains(lowercaseQuery)
                || account.getAccountName().toLowerCase().contains(lowercaseQuery)
                || account.getAddress().toLowerCase().contains(lowercaseQuery)
                || account.getEmail().toLowerCase().contains(lowercaseQuery)
                || account.getPhone().toLowerCase().contains(lowercaseQuery)
                || account.getStatus().toLowerCase().contains(lowercaseQuery)).collect(Collectors.toList());
    }
}
