package com.nbp.bear.components.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUserLoginRequest {

    private String userName;
    private String password;
}
