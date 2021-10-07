package com.medcallapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, name = "username") private String username;
    @Column(nullable = false, name = "full_name") private String fullName;
    @Column(nullable = false, name = "email") private String email;
    @Column(nullable = false, name = "password") private String password;
    @Column(name = "role") private String role;
    @Column(name = "verified") private Boolean verified = false;
    @Column(name = "address") private String address;
    @Column(name = "speciality") private String speciality;
    @Column(name = "phone") private Long phone;
    @Column(name = "cin") private Long cin;

    public UserEntity(
            String username,
            String fullName,
            String email,
            String password,
            String role,
            String address,
            String speciality,
            Long phone,
            Long cin
    ) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.speciality = speciality;
        this.phone = phone;
        this.cin = cin;
    }

}
