package com.medcallapi.service;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.PasswordRequest;
import com.medcallapi.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<UserEntity> updateInfos(String username, UserRequest userRequest) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            if (userRequest.getFullName() != null && !userRequest.getFullName().equals(user.getFullName())) user.setFullName(userRequest.getFullName());
            if (userRequest.getAddress() != null && !userRequest.getAddress().equals(user.getAddress())) user.setAddress(userRequest.getAddress());
            if (userRequest.getSpeciality() != null && !userRequest.getSpeciality().equals(user.getSpeciality())) user.setSpeciality(userRequest.getSpeciality());
            if (userRequest.getPhone() != null && !userRequest.getPhone().equals(user.getPhone())) user.setPhone(userRequest.getPhone());
            if (userRequest.getCin() != null && !userRequest.getCin().equals(user.getCin())) user.setCin(userRequest.getCin());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<Void> updatePassword(String username, PasswordRequest passwordRequest) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<UserEntity> show(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null ? ResponseEntity.status(HttpStatus.OK).body(user) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
