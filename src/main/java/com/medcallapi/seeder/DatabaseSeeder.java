package com.medcallapi.seeder;

import com.medcallapi.repository.ArticleRepository;
import com.medcallapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DatabaseSeeder {

    private UserRepository userRepository;
    private ArticleRepository articleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        (new UserSeeder(userRepository)).seedUsersTable();
        (new ArticleSeeder(articleRepository, userRepository)).seedArticleTable();
    }

}