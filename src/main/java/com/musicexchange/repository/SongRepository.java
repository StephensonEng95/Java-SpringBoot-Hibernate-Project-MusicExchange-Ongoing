package com.musicexchange.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musicexchange.models.Song;

import jakarta.transaction.Transactional;
/**
 * Song repository with implicit and custom queries
 * Extends CrudRepository for basic CRUD operations
 */
@Repository
public interface SongRepository extends CrudRepository<Song,Long>{
    	
	boolean existsBySongTitle(String songTitle);
	Optional<Song> findSongByTitle(String songTitle);

	List<Song> findByArtistId(Long artistId);
	
	@Transactional
	@Modifying
	@Query("delete from Songs s where s.songTitle =:songTitle")
	void deleteSongByTitle(@Param("songTitle")String songTitle);
}
