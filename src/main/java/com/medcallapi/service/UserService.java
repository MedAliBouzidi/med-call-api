package com.medcallapi.service;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.request.UserRequest;
import com.medcallapi.response.UserResponse;
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

    public ResponseEntity<UserResponse> updateInfos(String username, UserRequest userRequest) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            if (!userRequest.getUsername().equals(user.getUsername())) user.setUsername(userRequest.getUsername());
            if (!userRequest.getFullName().equals(user.getFullName())) user.setFullName(userRequest.getFullName());
            if (!userRequest.getEmail().equals(user.getEmail())) user.setEmail(userRequest.getEmail());
            if (!userRequest.getAddress().equals(user.getAddress())) user.setAddress(userRequest.getAddress());
            if (!userRequest.getSpeciality().equals(user.getSpeciality())) user.setSpeciality(userRequest.getSpeciality());
            if (!userRequest.getPhone().equals(user.getPhone())) user.setPhone(userRequest.getPhone());
            if (!userRequest.getCin().equals(user.getCin())) user.setCin(userRequest.getCin());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<UserResponse> updatePassword(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null && !passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity<UserResponse> show(String username) {
        UserEntity user = userRepository.findByUsername(username);
        return user != null ? ResponseEntity.status(HttpStatus.OK).body(new UserResponse(user)) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
