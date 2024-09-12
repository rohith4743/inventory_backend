package com.rohithkankipati.Inventory.dto;

import java.util.Set;

import com.rohithkankipati.Inventory.entity.UserEntity;
import com.rohithkankipati.Inventory.model.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
	private Long id;

    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    private String email;

    @NotBlank(message = "{username.required}")
    @Size(min = 3, max = 20, message = "{username.size}")
    @Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "username.pattern")
    private String userName;

    @NotBlank(message = "{firstname.required}")
    private String firstName;

    @NotBlank(message = "{lastname.required}")
    private String lastName;

    @NotBlank(message = "{password.required}")
    @Size(min = 8, message = "{password.size}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "{password.pattern}")
    private String password;

    @NotBlank(message = "{password.required}")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "{mobilenumber.invalid}")
    private String mobileNumber;

    private String jwtToken;
    
    private String storeName;

    private Set<UserRole> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}
    
	public UserDTO fromEntity(UserEntity user) {
		UserDTO userDto = new UserDTO();
		userDto.setEmail(user.getEmail());
		userDto.setUserName(user.getUserName());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setId(user.getId());
		userDto.setMobileNumber(user.getMobileNumber());
		userDto.setRoles(user.getRoles());
		userDto.setStoreName(user.getStoreName());
		return userDto;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

}