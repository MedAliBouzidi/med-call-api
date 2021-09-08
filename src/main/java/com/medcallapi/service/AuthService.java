package com.medcallapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.medcallapi.entity.ConfirmationToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailService emailSenderService;

    public ObjectNode register(UserEntity newUser) {
        UserEntity userByUsername = userRepository.findByUsername(newUser.getUsername());
        UserEntity userByEmail = userRepository.findByEmail(newUser.getEmail());

        ObjectNode node = (new ObjectMapper()).createObjectNode();

        if (userByUsername != null && Objects.equals(userByUsername.getUsername(), newUser.getUsername())){
            return node.put("error", "Username Already exists!");
        } else if (userByEmail != null && Objects.equals(userByEmail.getEmail(), newUser.getEmail())){
            return node.put("error", "Email Already exists!");
        }

        UserEntity createdUser = new UserEntity();
        createdUser.setUsername(newUser.getUsername());
        createdUser.setFullName(newUser.getFullName());
        createdUser.setEmail(newUser.getEmail());
        createdUser.setPassword((new BCryptPasswordEncoder()).encode(newUser.getPassword()));
        createdUser.setUserRole(newUser.getUserRole());

        switch (newUser.getUserRole()) {
            case ADMIN:
                createdUser.setCin(newUser.getCin());
                break;
            case PRO_SANTE:
                createdUser.setAddress(newUser.getAddress());
                createdUser.setPhone(newUser.getPhone());
                createdUser.setSpeciality(newUser.getSpeciality());
                break;
        }

        userRepository.save(createdUser);

        final ConfirmationToken confirmationToken = new ConfirmationToken(createdUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(createdUser.getEmail(), confirmationToken.getConfirmationToken());

        return node.put("success", "User created successfully!");
    }

    public void sendConfirmationMail(String userMail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Confirmation Link!");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account. http://localhost:8080/api/register/confirm?token="+token);

        emailSenderService.sendEmail(mailMessage);
    }
}