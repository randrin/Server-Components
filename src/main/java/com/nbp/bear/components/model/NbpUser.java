package com.nbp.bear.components.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name= "NBP_USERS")
public class NbpUser {

    @Id
    @GeneratedValue
    private int id;
    private String userName;
    private String password;
    private String email;
    private boolean isActive;
    private String roles; // ROLE_USER, ROLE_ADMIN
}
