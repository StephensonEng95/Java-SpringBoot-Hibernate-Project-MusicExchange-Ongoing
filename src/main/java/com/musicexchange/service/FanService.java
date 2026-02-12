package com.musicexchange.service;

import com.musicexchange.models.Fan;
import com.musicexchange.repository.FanRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class FanService {

    private final FanRepository fanRepo;
    private final PasswordEncoder passwordEncoder;

    public FanService(FanRepository fanRepo, PasswordEncoder passwordEncoder) {
        this.fanRepo = fanRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Main signup logic for fans
    public void createFan(String username, String email, String rawPassword) {
        log.info("Creating fan: {}", username);

        if (fanRepo.existsByUsername(username)) {
            throw new RuntimeException("Username '" + username + "' is already taken");
        }

        if (fanRepo.existsByEmail(email)) {
            throw new RuntimeException("Email '" + email + "' is already taken");
        }

        Fan fan = new Fan();
        fan.setUsername(username);
        fan.setEmail(email);
        fan.setPassword(passwordEncoder.encode(rawPassword));

        fanRepo.save(fan);
        log.debug("Fan saved successfully");
    }

    // Checks credentials for login
    public Optional<Fan> authenticateFan(String username, String rawPassword) {
        log.info("Auth attempt for fan: {}", username);
        Optional<Fan> fan = fanRepo.findByUsername(username);

        if (fan.isPresent() && passwordEncoder.matches(rawPassword, fan.get().getPassword())) {
            log.debug("Login successful");
            return fan;
        }

        log.error("Login failed for fan: {}", username);
        return Optional.empty();
    }

    // Updates existing email after checking if user exists
    public void updateFanEmail(Long id, String email) {
        log.info("Updating email for fan id: {}", id);

        Fan fan = fanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find Fan with id: " + id));

        fan.setEmail(email);
        fanRepo.save(fan);
        log.debug("Email updated successfully");
    }

    // Encodes new password before saving
    public void updateFanPassword(Long id, String password) {
        log.info("Updating password for fan id: {}", id);

        Fan fan = fanRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find Fan with id: " + id));

        fan.setPassword(passwordEncoder.encode(password));
        fanRepo.save(fan);
    }

    // Removes fan from system
    public void deleteFan(Long id) {
        log.info("Deleting fan id: {}", id);

        if (!fanRepo.existsById(id)) {
            throw new RuntimeException("Delete failed: Fan not found");
        }

        fanRepo.deleteById(id);
    }
}