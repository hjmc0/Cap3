package com.uob.cap3;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long tid;
    
    private String tname;
    private String tpass;
    
}
