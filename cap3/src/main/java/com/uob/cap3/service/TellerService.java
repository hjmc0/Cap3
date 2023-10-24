package com.uob.cap3.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class TellerService {

    public boolean isValidLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
             return true;
        } else {
            System.out.println("telser");
            return false;
        }
    }
}