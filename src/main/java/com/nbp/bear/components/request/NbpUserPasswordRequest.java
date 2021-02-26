package com.nbp.bear.components.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NbpUserPasswordRequest {
    private String email;
    private String password;
}
