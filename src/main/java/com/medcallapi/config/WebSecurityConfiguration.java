package com.medcallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/api").permitAll()
                .antMatchers(HttpMethod.GET, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST,"/api/register").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/register/confirm").permitAll()
                .anyRequest()
                .authenticated();
    }
}