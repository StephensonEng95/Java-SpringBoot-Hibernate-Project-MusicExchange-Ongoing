package com.musicexchange.musicexchange.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musicexchange.musicexchange.models.Songs;

import jakarta.transaction.Transactional;

@Repository
public interface SongsRepository extends CrudRepository<Songs,Long>{

	@Modifying
	@Transactional
	@Query("delete from Songs s where s.songId=:songId")
	void deleteSong(@Param("songId")Long songId);
}
