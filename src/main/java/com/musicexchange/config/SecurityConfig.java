package com.musicexchange.config;

import com.musicexchange.repository.ArtistRepository;
import com.musicexchange.repository.FanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MyAuthenticationSuccessHandler successHandler;
    private final ArtistRepository artistRepository;
    private final FanRepository fanRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var artistOpt = artistRepository.findByUsername(username);
            if (artistOpt.isPresent()) {
                var artist = artistOpt.get();
                return User.builder()
                        .username(artist.getUsername())
                        .password(artist.getPassword())
                        .roles("ARTIST")
                        .build();
            }

            var fanOpt = fanRepository.findByUsername(username);
            if (fanOpt.isPresent()) {
                var fan = fanOpt.get();
                return User.builder()
                        .username(fan.getUsername())
                        .password(fan.getPassword())
                        .roles("FAN")
                        .build();
            }

            throw new UsernameNotFoundException("User not found: " + username);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/signup", "/api/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/song/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/song/**").authenticated()
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler) // Cleanly injects the separate handler component
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }
}
