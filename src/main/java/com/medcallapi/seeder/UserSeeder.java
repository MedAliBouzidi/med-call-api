package com.medcallapi.seeder;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserSeeder {

    private final UserRepository userRepository;

    public UserSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void seedUsersTable() {
        UserEntity admin = new UserEntity(
                "admin",
                "Admin Admin",
                "admin@admin.com",
                "$2a$10$aZB1Rc1uVFVxy2dKbdguBurXHJLX1tiz.4gxfzt5H3XrrF53q6uCS", //12345678
                "ADMIN",
                null,
                null,
                null,
                12345678L
        );
        UserEntity pro_sante = new UserEntity(
                "doctor",
                "Dr Med",
                "dr@dr.com",
                "$2a$10$aZB1Rc1uVFVxy2dKbdguBurXHJLX1tiz.4gxfzt5H3XrrF53q6uCS", //12345678
                "PRO_SANTE",
                "some address",
                "some speciality",
                23232323L,
                12345678L
        );
        UserEntity patient = new UserEntity(
                "patient",
                "Patient",
                "patient@pt.com",
                "$2a$10$aZB1Rc1uVFVxy2dKbdguBurXHJLX1tiz.4gxfzt5H3XrrF53q6uCS", //12345678
                "PATIENT",
                null,
                null,
                null,
                null
        );

        admin.setVerified(true);
        pro_sante.setVerified(true);
        patient.setVerified(true);

        userRepository.save(admin);
        userRepository.save(pro_sante);
        userRepository.save(patient);
    }
}
