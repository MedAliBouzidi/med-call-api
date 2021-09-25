package com.medcallapi.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class RegistrationResponse {
    private String usernameExist;
    private String emailExist;
    private String success;
}
