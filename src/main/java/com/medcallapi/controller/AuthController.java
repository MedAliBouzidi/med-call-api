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
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api")
@CrossOrigin
@AllArgsConstructor
public class AuthController {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthService authService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    @PostMapping(path = "/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return this.authService.login(authenticationRequest);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest registrationRequest) throws MessagingException {
        return this.authService.register(registrationRequest);
    }

    @DeleteMapping("/register/confirm")
    public void confirmUser(@RequestParam String token) {
        confirmationTokenService.confirmUser(confirmationTokenRepository.findByConfirmationToken(token));
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        return this.authService.resetPassword(resetPasswordRequest);
    }

    @PatchMapping(path = "/reset-password")
    public void updatePassword(
            @RequestParam String token,
            @RequestBody String newPassword
    ) {
        resetPasswordTokenService.updatePassword(resetPasswordTokenRepository.findByResetPasswordToken(token), newPassword);
    }
}
