package com.medcallapi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor @Getter
@ToString
@Entity
@Table(name = "users")
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "username")
    private String username;

    @Column(nullable = false, name = "full_name")
    private String fullName;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "address")
    private String address;

    @Column(name = "speciality")
    private String speciality;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "cin")
    private Long cin;

    public UserEntity(
            String username,
            String fullName,
            String email,
            String password,
            UserRole userRole,
            String address,
            String speciality,
            Long phone,
            Long cin
    ) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.address = address;
        this.speciality = speciality;
        this.phone = phone;
        this.cin = cin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public void setCin(Long cin) {
        this.cin = cin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
