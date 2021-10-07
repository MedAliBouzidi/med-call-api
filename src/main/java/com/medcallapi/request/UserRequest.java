package com.medcallapi.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class UserRequest {
    private String username;
    private String fullName;
    private String email;
    private String address;
    private String speciality;
    private Long phone;
    private Long cin;
}
