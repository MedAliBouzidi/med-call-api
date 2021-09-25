package com.medcallapi.service;

import com.medcallapi.GlobalVariable;
import com.medcallapi.entity.ConfirmationToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.request.AuthenticationRequest;
import com.medcallapi.response.AuthenticationResponse;
import com.medcallapi.repository.UserRepository;
import com.medcallapi.utils.JwtUtiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    @Autowired private UserRepository userRepository;
    @Autowired private ConfirmationTokenService confirmationTokenService;
    @Autowired private EmailService emailSenderService;
    @Autowired private JwtUtiles jwtUtiles;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, String> register(UserEntity newUser) {
        UserEntity userByUsername = userRepository.findByUsername(newUser.getUsername());
        UserEntity userByEmail = userRepository.findByEmail(newUser.getEmail());

        Map<String, String> message = new HashMap<>();

        if (userByUsername != null && Objects.equals(userByUsername.getUsername(), newUser.getUsername())){
            message.put("error", "Username Already exists!");
            return message;
        } else if (userByEmail != null && Objects.equals(userByEmail.getEmail(), newUser.getEmail())){
            message.put("error", "Email Already exists!");
            return message;
        }

        UserEntity createdUser = new UserEntity();
        createdUser.setUsername(newUser.getUsername());
        createdUser.setFullName(newUser.getFullName());
        createdUser.setEmail(newUser.getEmail());
        createdUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        createdUser.setRoles(newUser.getRoles());

        switch (newUser.getRoles()) {
            case "ADMIN":
                createdUser.setCin(newUser.getCin());
                break;
            case "PRO_SANTE":
                createdUser.setAddress(newUser.getAddress());
                createdUser.setPhone(newUser.getPhone());
                createdUser.setSpeciality(newUser.getSpeciality());
                break;
        }

        userRepository.save(createdUser);

        final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(createdUser.getEmail(), confirmationToken.getConfirmationToken());

        message.put("success", "User created successfully!");
        return message;
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
        if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
            String jwt = jwtUtiles.generateToken(user);
            return ResponseEntity.ok(new AuthenticationResponse(jwt));
        }
        return ResponseEntity.notFound().build();

    }
}