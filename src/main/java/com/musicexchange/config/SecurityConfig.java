package com.musicexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // I am using BCrypt to handle password encryption for MySQL.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // I am disabling CSRF so our custom POST login works without tokens.
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // I am permitting these routes so they are accessible to everyone.
                        .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        // I added these to prevent the 403 Access Denied error on the dashboard.
                        .requestMatchers("/song/**", "/artist/**", "/fan/**").permitAll()
                        .anyRequest().permitAll()
                )

                // I removed .formLogin() so the UserController can handle the session.

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }
}