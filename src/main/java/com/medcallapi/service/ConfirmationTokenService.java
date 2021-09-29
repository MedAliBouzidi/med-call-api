package com.medcallapi.service;

import com.medcallapi.entity.ConfirmationToken;
import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.ConfirmationTokenRepository;
import com.medcallapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;

    void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    void deleteConfirmationToken(Long id) {
        confirmationTokenRepository.deleteById(id);
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        final UserEntity user = confirmationToken.getUser();

        user.setVerified(true);
        userRepository.save(user);
        this.deleteConfirmationToken(confirmationToken.getId());
    }

}
