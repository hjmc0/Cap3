package com.uob.cap3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.uob.cap3.entities.Role;
import com.uob.cap3.entities.Teller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TellerDetails implements UserDetails {

    @Autowired
    Teller teller;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> tellerRoles = teller.getTellerRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tellerRole : tellerRoles) {
            authorities.add(new SimpleGrantedAuthority(tellerRole.getRoleName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String getPassword() {
        return teller.getTellerPass();
    }

    @Override
    public String getUsername() {
        return teller.getTellerName();
    }

    @Override
    public boolean isEnabled() {
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
    }
    
    
}
