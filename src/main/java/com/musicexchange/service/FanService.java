package com.musicexchange.service;

import com.musicexchange.models.Fan;
import com.musicexchange.repository.FanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FanService {

    private final FanRepository fanRepo;

    // Standard constructor injection for the repository
    public FanService(FanRepository fanRepo) {
        this.fanRepo = fanRepo;
    }

    // Creating a new fan with the original validation logic
    public void createFan(String username, String email, String password) {
        if (fanRepo.existsByUsername(username)) {
            throw new RuntimeException("Fan username already taken.");
        }
        if (fanRepo.existsByEmail(email)) {
            throw new RuntimeException("Fan email already registered.");
        }

        Fan fan = new Fan();
        fan.setUsername(username);
        fan.setEmail(email);
        fan.setPassword(password);
        fanRepo.save(fan);
    }

    // Pulls the full list of fans
    public List<Fan> getAllFans() {
        return fanRepo.findAll();
    }

    // Fetching a fan by the specific fan_id
    public Fan getFanById(Long fan_id) {
        return fanRepo.findById(fan_id)
                .orElseThrow(() -> new RuntimeException("Fan not found for ID: " + fan_id));
    }

    // Updates an existing fan record in the DB
    public void updateFan(Fan fan) {
        fanRepo.save(fan);
    }

    // Deletion logic based on the fan_id primary key
    public void deleteFan(Long fan_id) {
        if (!fanRepo.existsById(fan_id)) {
            throw new RuntimeException("Delete failed: Fan ID " + fan_id + " does not exist.");
        }
        fanRepo.deleteById(fan_id);
    }
}