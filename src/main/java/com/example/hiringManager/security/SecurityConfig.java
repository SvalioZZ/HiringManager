package com.example.hiringManager.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                                   .username("user")
                                   .password("{noop}password")
                                   .roles("USER")
                                   .build();
        UserDetails admin = User.builder()
                                    .username("admin")
                                    .password("{noop}password")
                                    .roles("USER", "ADMIN")
                                    .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> {
                    try {
                        http.csrf(AbstractHttpConfigurer::disable).cors(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll()
                                                                       .anyRequest().authenticated()
                                );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        
        return http.build();
    }
    
}