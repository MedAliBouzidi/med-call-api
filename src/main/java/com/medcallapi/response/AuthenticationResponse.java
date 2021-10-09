package com.medcallapi.response;

import com.medcallapi.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AuthenticationResponse {

    private UserEntity user;
    private String jwt;

}
