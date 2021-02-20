package com.nbp.bear.components.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nbp.bear.components.constant.NbpConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name= "NBP_USERS")
public class NbpUser {

    @Id
    @GeneratedValue
    private int id ;

    @NotNull(message = NbpConstant.NBP_USERNAME_REQUIRED)
    private String userName;

    @NotNull(message = NbpConstant.NBP_PASSWORD_REQUIRED)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = NbpConstant.NBP_EMAIL_REQUIRED)
    private String email;

    private boolean isActive;
    private String roles; // ROLE_USER, ROLE_ADMIN
}
