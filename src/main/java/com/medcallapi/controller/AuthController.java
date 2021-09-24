package com.medcallapi.controller;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ConfirmationTokenRepository;
import com.medcallapi.service.ConfirmationTokenService;
import com.medcallapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping(path = "/login")
    public String login(@RequestBody UserEntity user) {
        return this.authService.login(user);
    }

    @PostMapping(
            path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, String> register(@RequestBody UserEntity newUser) {
       return this.authService.register(newUser);
    }

    @DeleteMapping("/register/confirm")
    public void confirmUser(@RequestParam String token) {
        confirmationTokenService.confirmUser(
                confirmationTokenRepository.findByConfirmationToken(token)
        );
    }

}
