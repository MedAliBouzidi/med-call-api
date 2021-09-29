package com.medcallapi.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class ResetPasswordResponse {
    private String error;
    private String success;
}
