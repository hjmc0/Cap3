package com.uob.cap3.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tellerId;
    
    private String tellerName;
    private String tellerPass;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_teller", joinColumns = @JoinColumn(name = "teller_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> tellerRoles = new HashSet<>();


    
}
