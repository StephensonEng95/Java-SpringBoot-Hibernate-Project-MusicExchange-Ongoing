package com.musicexchange.repository;

import com.musicexchange.models.Fan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FanRepository extends JpaRepository<Fan, Long> {

    // For login authentication
    Optional<Fan> findByUsername(String username);

    // To check if username is available at signup
    boolean existsByUsername(String username);

    // To check if email is already in the system
    boolean existsByEmail(String email);
}