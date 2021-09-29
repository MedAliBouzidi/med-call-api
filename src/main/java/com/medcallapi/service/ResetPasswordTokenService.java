package com.medcallapi.service;

import com.medcallapi.entity.ResetPasswordToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ResetPasswordTokenRepository;
import com.medcallapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ResetPasswordTokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    void saveResetPasswordToken(ResetPasswordToken resetPasswordToken) {
        resetPasswordTokenRepository.save(resetPasswordToken);
    }

    void deleteResetPasswordToken(Long id) {
        resetPasswordTokenRepository.deleteById(id);
    }

    public void updatePassword(ResetPasswordToken resetPasswordToken, String newPassword) {
        final UserEntity user = resetPasswordToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        this.deleteResetPasswordToken(resetPasswordToken.getId());
    }

}
