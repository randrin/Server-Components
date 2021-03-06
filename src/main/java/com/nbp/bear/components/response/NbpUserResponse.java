package com.nbp.bear.components.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUserResponse {

    private String message;
    private String jwtToken;

}
