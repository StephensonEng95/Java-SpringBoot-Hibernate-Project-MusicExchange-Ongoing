package com.musicexchange.repository;

import com.musicexchange.models.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {

    // Finds a fan by username for login checks
    Optional<Fan> findByUsername(String username);

    // Checks for existing users during the signup process
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}