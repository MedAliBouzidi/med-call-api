package com.medcallapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor  @AllArgsConstructor
@Getter @Setter
public class ResetPasswordRequest {
    private String email;
}
