package com.nbp.bear.components.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NbpUser {

    private int id;
    private String userName;
    private String password;
    private String email;
}
