package com.nbp.bear.components.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUserRegisterRequest {

    private String userName;
    private String email;
    private String password;
    // Some fields later will be set
}
