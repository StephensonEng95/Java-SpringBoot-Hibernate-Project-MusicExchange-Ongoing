package com.musicexchange.musicexchange.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musicexchange.musicexchange.models.Artist;

import jakarta.transaction.Transactional;



@Repository
public interface ArtistRepository extends CrudRepository<Artist,Long>{
	
	//void createArtist(String username,String password,String email);
	
	@Modifying
	@Transactional
	@Query("update Artist a set a.email= :email where a.id= :id")
	void updateArtistEmail(@Param("id")Long id,@Param("email")String email);
	
	@Modifying
	@Transactional
	@Query("update Artist a set a.password= :password where a.id= :id")
	void updateArtistPassword(@Param("id")Long id,@Param("password")String password);
	
	@Modifying
	@Transactional
	@Query("delete from Artist a where a.id= :id")
	void deleteArtist(@Param("id")Long id);
	
	
	
	
	
	

}
