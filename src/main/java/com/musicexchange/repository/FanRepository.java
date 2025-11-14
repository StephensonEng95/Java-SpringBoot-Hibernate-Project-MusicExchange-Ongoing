package com.musicexchange.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.musicexchange.models.Fan;

import jakarta.transaction.Transactional;
/**
 * Fan repository with implicit and custom queries
 * Extends CrudRepository for basic CRUD operations
 */
@Repository
public interface FanRepository extends CrudRepository<Fan,Long> {
	
	public Fan createFan(String username, String email, String password);
	
	Optional<Fan> findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String password);
	
	@Transactional
	@Modifying
	@Query("update Fan f set f.email= :email where f.id= :id") 
	void updateFanEmail(@Param("id")Long id, @Param("email")String email);
	
	@Transactional
	@Modifying
	@Query("udate Fan f set f.password= :email where f.id= :id")
	void updateFanPassword(@Param("id")Long id, @Param("password")String password);
	
	@Transactional
	@Modifying
	@Query("delete from Fan f where f.id= :id")
	void deleteFan(@Param("id")Long id);
}
