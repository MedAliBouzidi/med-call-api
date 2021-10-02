package com.medcallapi.service;

import com.medcallapi.GlobalVariables;
import com.medcallapi.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendConfirmationMail(UserEntity user, String token) throws MessagingException {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("confirm_url", GlobalVariables.UI_BASE_URL +"register/confirm?token="+token);

        String process = templateEngine.process("emails/confirm-email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Confirmation Link!");
        helper.setText(process, true);
        helper.setTo(user.getEmail());
        javaMailSender.send(mimeMessage);
    }

    public void sendResetPasswordMail(UserEntity user, String token) throws MessagingException {
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("reset_password_url", GlobalVariables.UI_BASE_URL +"reset-password?token="+token);

        String process = templateEngine.process("emails/reset-password-email", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Reset Password Link!");
        helper.setText(process, true);
        helper.setTo(user.getEmail());
        javaMailSender.send(mimeMessage);
    }

}
