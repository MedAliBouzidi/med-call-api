package com.medcallapi.controller;

import com.medcallapi.repository.ResetPasswordTokenRepository;
import com.medcallapi.request.AuthenticationRequest;
import com.medcallapi.request.RegistrationRequest;
import com.medcallapi.request.ResetPasswordRequest;
import com.medcallapi.response.AuthenticationResponse;
import com.medcallapi.repository.ConfirmationTokenRepository;
import com.medcallapi.response.RegistrationResponse;
import com.medcallapi.response.ResetPasswordResponse;
import com.medcallapi.service.ConfirmationTokenService;
import com.medcallapi.service.AuthService;
import com.medcallapi.service.ResetPasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {
    @Autowired private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired private ConfirmationTokenService confirmationTokenService;
    @Autowired private AuthService authService;
    @Autowired private ResetPasswordTokenService resetPasswordTokenService;
    @Autowired private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return this.authService.login(authenticationRequest);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        return this.authService.register(registrationRequest);
    }

    @DeleteMapping("/register/confirm")
    public void confirmUser(@RequestParam String token) {
        confirmationTokenService.confirmUser(
                confirmationTokenRepository.findByConfirmationToken(token)
        );
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return this.authService.resetPassword(resetPasswordRequest);
    }

    @PatchMapping(path = "/reset-password")
    public void updatePassword(
            @RequestParam String token,
            @RequestBody String password
    ) {
        resetPasswordTokenService.updatePassword(
                resetPasswordTokenRepository.findByResetPasswordToken(token),
                password
        );
    }
}
