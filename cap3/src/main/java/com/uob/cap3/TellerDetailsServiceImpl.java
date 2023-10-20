package com.uob.cap3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.uob.cap3.entities.Teller;
import com.uob.cap3.repo.TellerRepo;

public class TellerDetailsServiceImpl implements UserDetailsService {
    @Autowired
    TellerRepo tellerRepo;

    @Override
    public UserDetails loadUserByUsername(String tellerName) throws UsernameNotFoundException {
        Teller teller = tellerRepo.findByTellerName(tellerName);

        if (teller == null) {
            throw new UsernameNotFoundException("Teller Name not found");
        }

        return new TellerDetails(teller);
    }
}
