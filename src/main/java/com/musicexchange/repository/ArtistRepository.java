package com.musicexchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musicexchange.models.Artist;

import jakarta.transaction.Transactional;

/**
 * Repository for Artist entities with implicit and custom queries
 * Extends CrudRepository for basic CRUD operations
 */

@Repository
public interface ArtistRepository extends CrudRepository<Artist,Long>{
	
	
	public Artist createArtist(String username,String email, String password );
	
	Optional<Artist> findByUsername(String username); 
	boolean existsByUsername(String username); 
	boolean existsByEmail(String email);
	
	@Transactional
	@Modifying
	@Query("update Artist a set a.email= :email where a.id= :id")
	void updateArtistEmail(@Param("id")Long id,@Param("email")String email);
	
	
	@Transactional
	@Modifying
	@Query("update Artist a set a.password= :password where a.id= :id")
	void updateArtistPassword(@Param("id")Long id,@Param("password")String password);
	
	@Transactional
	@Modifying
	@Query("delete from Artist a where a.id= :id")
	void deleteArtist(@Param("id")Long id);

}
