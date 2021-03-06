package com.nbp.bear.components.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUserUpdateRequest {

    private String userName;
    private  String email;
    // Some fields later will be set
}
