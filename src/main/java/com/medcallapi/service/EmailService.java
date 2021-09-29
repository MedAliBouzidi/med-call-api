package com.medcallapi.service;

import com.medcallapi.GlobalVariable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationMail(String userMail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Confirmation Link!");
        mailMessage.setText("Thank you for registering. Please click on the below link to activate your account. "+ GlobalVariable.UI_BASE_URL +"register/confirm?token="+token);

        sendEmail(mailMessage);
    }

    public void sendResetPasswordMail(String userMail, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("Reset Password Link!");
        mailMessage.setText("You can update your password from this link please make it easy to remember. "+ GlobalVariable.UI_BASE_URL +"reset-password?token="+token);

        sendEmail(mailMessage);
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

}
