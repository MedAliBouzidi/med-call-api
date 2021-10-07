package com.medcallapi.service.admin;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminUserService {

    private UserRepository userRepository;

    public List<UserEntity> index() {
        return userRepository.findAll();
    }

    public ResponseEntity<Void> destroy(String username) {
        UserEntity user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
