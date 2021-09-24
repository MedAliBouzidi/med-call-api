package com.medcallapi.seeder;

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
        (new UserSeeder(userRepository)).seedUsersTable();
    }

}