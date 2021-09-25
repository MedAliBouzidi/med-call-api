package com.medcallapi.controller;

import com.medcallapi.request.AuthenticationRequest;
import com.medcallapi.request.RegistrationRequest;
import com.medcallapi.response.AuthenticationResponse;
import com.medcallapi.repository.ConfirmationTokenRepository;
import com.medcallapi.response.RegistrationResponse;
import com.medcallapi.service.ConfirmationTokenService;
import com.medcallapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {
    @Autowired private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired private AuthService authService;
    @Autowired private ConfirmationTokenService confirmationTokenService;

    @GetMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return this.authService.login(authenticationRequest);
    }

    @PostMapping(
            path = "/register",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return this.authService.register(registrationRequest);
    }

    @DeleteMapping("/register/confirm")
    public void confirmUser(@RequestParam String token) {
        confirmationTokenService.confirmUser(
                confirmationTokenRepository.findByConfirmationToken(token)
        );
    }

}
