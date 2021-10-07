package com.medcallapi.service;

import com.medcallapi.entity.ConfirmationToken;
import com.medcallapi.entity.ResetPasswordToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.request.AuthenticationRequest;
import com.medcallapi.request.RegistrationRequest;
import com.medcallapi.request.ResetPasswordRequest;
import com.medcallapi.response.AuthenticationResponse;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.response.RegistrationResponse;
import com.medcallapi.response.ResetPasswordResponse;
import com.medcallapi.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final ResetPasswordTokenService resetPasswordTokenService;
    private final EmailService emailSenderService;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<RegistrationResponse> register(RegistrationRequest registrationRequest) throws MessagingException {
        UserEntity userByUsername = userRepository.findByUsername(registrationRequest.getUsername());
        UserEntity userByEmail = userRepository.findByEmail(registrationRequest.getEmail());

        RegistrationResponse response = new RegistrationResponse();

        if (userByUsername != null && userByUsername.getUsername().equals(registrationRequest.getUsername())){
            response.setUsernameExist("Username Already exists!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (userByEmail != null && userByEmail.getEmail().equals(registrationRequest.getEmail())){
            response.setEmailExist("Email Already exists!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserEntity createdUser = new UserEntity();
        createdUser.setUsername(registrationRequest.getUsername());
        createdUser.setFullName(registrationRequest.getFullName());
        createdUser.setEmail(registrationRequest.getEmail());
        createdUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        createdUser.setRole(registrationRequest.getRole());

        switch (registrationRequest.getRole()) {
            case "ADMIN":
                createdUser.setCin(registrationRequest.getCin());
                break;
            case "PRO_SANTE":
                createdUser.setAddress(registrationRequest.getAddress());
                createdUser.setPhone(registrationRequest.getPhone());
                createdUser.setSpeciality(registrationRequest.getSpeciality());
                break;
        }

        userRepository.save(createdUser);

        final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        emailSenderService.sendConfirmationMail(createdUser, confirmationToken.getConfirmationToken());

        response.setSuccess("User created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest){
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail());

        if (user != null && passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            String jwt = jwtUtils.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(user, jwt));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<ResetPasswordResponse> resetPassword(ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        UserEntity user = userRepository.findByEmail(resetPasswordRequest.getEmail());
        ResetPasswordResponse response = new ResetPasswordResponse();

        if (user != null) {
            final ResetPasswordToken resetPasswordToken = new ResetPasswordToken(user);
            resetPasswordTokenService.saveResetPasswordToken(resetPasswordToken);

            emailSenderService.sendResetPasswordMail(user, resetPasswordToken.getResetPasswordToken());
            response.setSuccess("Reset password link was send to your email address!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        response.setError("There is no user linked to this email !");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}