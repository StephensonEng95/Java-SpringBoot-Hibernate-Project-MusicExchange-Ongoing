package com.musicexchange.musicexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musicexchange.musicexchange.models.Songs;

@Repository
public interface SongsRepository extends JpaRepository<Songs,Long>{

}
