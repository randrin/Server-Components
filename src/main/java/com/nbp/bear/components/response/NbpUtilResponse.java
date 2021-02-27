package com.nbp.bear.components.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpUtilResponse {

    private String message;
    private Object object;
}
