package com.rohithkankipati.Inventory.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rohithkankipati.Inventory.dto.UserDTO;
import com.rohithkankipati.Inventory.entity.UserEntity;
import com.rohithkankipati.Inventory.exception.InventoryException;
import com.rohithkankipati.Inventory.model.UserRole;
import com.rohithkankipati.Inventory.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createAccount(UserDTO userDTO) {
		if (userRepository.existsByUserName(userDTO.getUserName())) {
		    throw new InventoryException("create_user.username.exists", HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(userDTO.getEmail())) {
		    throw new InventoryException("create_user.email.exists", HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByMobileNumber(userDTO.getMobileNumber())) {
		    throw new InventoryException("create_user.phone.exists", HttpStatus.BAD_REQUEST);
		}
	
		if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
		    userDTO.setRoles(Collections.singleton(UserRole.CUSTOMER));
		}
	
		UserEntity userEntity = new UserEntity();
		userEntity.fromUserDTO(userDTO);
		userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		userEntity.setDateCreated(LocalDateTime.now());
		userEntity.setLastModified(LocalDateTime.now());
	
		userEntity = userRepository.save(userEntity);
		return userDTO.fromEntity(userEntity);
    }

    public UserDTO login(String identifier, String password) {

		Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmailOrPhoneNumber(identifier);
	
		if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
		    return userOptional.get().toUserDTO();
		}
		throw new InventoryException("login.failed", HttpStatus.BAD_REQUEST);
    }

    public boolean checkUsernameExists(String username) {
    	return userRepository.existsByUserName(username);
    }


    public UserDTO getProfile(String userName) {

		Optional<UserEntity> userOptional = userRepository.findByUserName(userName);
		if (userOptional.isPresent()) {
		    UserEntity userEntity = userOptional.get();
		    UserDTO userDTO = new UserDTO();
		    userDTO.setEmail(userEntity.getEmail());
		    userDTO.setFirstName(userEntity.getFirstName());
		    userDTO.setLastName(userEntity.getLastName());
		    userDTO.setMobileNumber(userEntity.getMobileNumber());
		    userDTO.setUserName(userName);
		    return userDTO;
		}
			throw new InventoryException("profile.not_found", HttpStatus.BAD_REQUEST);
    }

    
}
