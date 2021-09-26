package com.medcallapi.response;

import com.medcallapi.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {

    private final UserEntity user;
    private final String jwt;

}
