package com.rohithkankipati.Inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rohithkankipati.Inventory.dto.LoginRequestDTO;
import com.rohithkankipati.Inventory.dto.UserDTO;
import com.rohithkankipati.Inventory.exception.InventoryException;
import com.rohithkankipati.Inventory.service.UserService;
import com.rohithkankipati.Inventory.util.JwtTokenUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
		try {
		    UserDTO user = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
		    String token = jwtTokenUtil.generateToken(user);
		    user.setJwtToken(token);
		    return ResponseEntity.ok().body(user);
		} catch (InventoryException e) {
		    throw e;
		} catch (Exception e) {
			System.out.println("here\n\n\n\n\n\n");
			throw e;
		}
    }

    @PostMapping("/api/create")
    public ResponseEntity<UserDTO> createAccount(@RequestBody @Valid UserDTO userDto) {
		try {
	
		    UserDTO createdUser = userService.createAccount(userDto);
		    String token = jwtTokenUtil.generateToken(createdUser);
		    createdUser.setJwtToken(token);
		    return ResponseEntity.ok(createdUser);
		} catch (InventoryException e) {
		    throw e;
		}
    }

    @GetMapping("/profile/{userName}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable @NotBlank(message = "username.required")
    	@Size(min = 3, max = 255, message = "username.size")
		@Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "username.pattern") String userName) {

		try {
		    UserDTO createdUser = userService.getProfile(userName);
		    return ResponseEntity.ok(createdUser);
		} catch (InventoryException e) {
		    throw e;
		}

    }

    @GetMapping("/api/username-exists")
	public ResponseEntity<?> checkUsernameExists(@RequestParam @NotBlank(message = "username.required")
	    @Size(min = 3, max = 255, message = "username.size")
		@Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "username.pattern") String username) {
		
    	boolean exists = userService.checkUsernameExists(username);
		return ResponseEntity.ok().body("{\"exists\": " + exists + "}");
    }


}
