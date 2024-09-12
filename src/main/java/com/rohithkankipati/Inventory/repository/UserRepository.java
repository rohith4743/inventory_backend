package com.rohithkankipati.Inventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rohithkankipati.Inventory.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	Optional<UserEntity> findByUserName(String userName);

    Optional<UserEntity> findByEmail(String email);
    
    @Query("SELECT u FROM UserEntity u WHERE u.userName = ?1 OR u.email = ?1 OR u.mobileNumber = ?1")
    Optional<UserEntity> findByUsernameOrEmailOrPhoneNumber(String identifier);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByMobileNumber(String mobileNumber);

}
