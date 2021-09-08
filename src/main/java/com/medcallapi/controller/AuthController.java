package com.medcallapi.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ConfirmationTokenRepository;
import com.medcallapi.service.ConfirmationTokenService;
import com.medcallapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping("/register")
    public ObjectNode register(@RequestBody UserEntity newUser) {
       return authService.register(newUser);
    }

    @DeleteMapping("/register/confirm")
    public void confirmUser(@RequestParam String token) {
        confirmationTokenService.confirmUser(
                confirmationTokenRepository.findByConfirmationToken(token)
        );
    }

}
