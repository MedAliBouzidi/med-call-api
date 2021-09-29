package com.medcallapi.response;

import com.medcallapi.entity.UserEntity;
import lombok.Getter;


@Getter
public class AuthenticationResponse {

    private final UserEntity user;
    private final String jwt;

    public AuthenticationResponse(UserEntity user, String jwt) {
        this.user = user;
        this.user.setPassword("");
        this.jwt = jwt;
    }
}
