package com.medcallapi.service;

import com.medcallapi.GlobalVariable;
import com.medcallapi.entity.ConfirmationToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.request.AuthenticationRequest;
import com.medcallapi.request.RegistrationRequest;
import com.medcallapi.response.AuthenticationResponse;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.response.RegistrationResponse;
import com.medcallapi.utils.JwtUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private ConfirmationTokenService confirmationTokenService;
    @Autowired private EmailService emailSenderService;
    @Autowired private JwtUtiles jwtUtiles;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<RegistrationResponse> register(RegistrationRequest registrationRequest) {
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

        sendConfirmationMail(createdUser.getEmail(), confirmationToken.getConfirmationToken());

        response.setSuccess("User created successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public void sendConfirmationMail(String userMail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Confirmation Link!");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account. "+ GlobalVariable.UI_BASE_URL +"register/confirm?token="+token);

        emailSenderService.sendEmail(mailMessage);
    }

    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest authenticationRequest){
        UserEntity user = userRepository.findByEmail(authenticationRequest.getEmail());

        if (user != null && passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            String jwt = jwtUtiles.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(user, jwt));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}