package com.uob.cap3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uob.cap3.entities.Teller;
import com.uob.cap3.repo.TellerRepo;

@Service
public class TellerService {
    @Autowired
    TellerRepo tr;

    public List<Teller> searchTellers(String query) {
        List<Teller> tellers = new ArrayList<>();
        tellers = (List<Teller>) tr.findAll();
        String lowercaseQuery = query.toLowerCase();
        return tellers.stream().filter(teller -> (String.valueOf(teller.getTellerId())).contains(lowercaseQuery)
                || teller.getTellerName().toLowerCase().contains(lowercaseQuery)).collect(Collectors.toList());
    }
}