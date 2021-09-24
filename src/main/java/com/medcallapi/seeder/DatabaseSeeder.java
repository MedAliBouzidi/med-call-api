package com.medcallapi.seeder;

import com.medcallapi.entity.UserEntity;
import com.medcallapi.entity.UserRole;
import com.medcallapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {

    @Autowired
    private UserRepository userRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUsersTable();
    }

    private void seedUsersTable() {
        UserEntity admin = new UserEntity(
                "admin",
                "Admin Admin",
                "admin@admin.com",
                "12345678",
                UserRole.ADMIN,
                null,
                null,
                null,
                (long) 12345678
        );
        UserEntity pro_sante = new UserEntity(
                "doctor",
                "Dr Med",
                "dr@dr.com",
                "12345678",
                UserRole.PRO_SANTE,
                "some address",
                "some speciality",
                (long) 23232323,
                (long) 12345678
        );
        UserEntity patient = new UserEntity(
                "medali",
                "Med Ali",
                "med4insta@gmail.com",
                "12345678",
                UserRole.PATIENT,
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