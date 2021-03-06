package com.nbp.bear.components.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUserChangePasswordRequest {

    private String OldPassword;
    private String newPassword;
}
