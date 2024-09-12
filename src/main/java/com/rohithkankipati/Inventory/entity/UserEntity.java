package com.rohithkankipati.Inventory.entity;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.rohithkankipati.Inventory.dto.UserDTO;
import com.rohithkankipati.Inventory.model.UserRole;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(unique = true)
    private String email;

	@Column(name = "username", unique = true)
    private String userName;

	@Column(name = "firstname")
    private String firstName;

	@Column(name = "lastname")
    private String lastName;

    private String password;

    @Column(name = "date_created")
    @CreatedDate
    private LocalDateTime dateCreated;

    @Column(name = "last_modified", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastModified;

    @Column(name = "mobile", unique = true)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<UserRole> roles;
    
    @Column(name = "storename")
    private String storeName;

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

    public LocalDateTime getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
	this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastModified() {
	return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
	this.lastModified = lastModified;
    }

    public String getMobileNumber() {
	return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void fromUserDTO(UserDTO userDTO) {
		this.id = userDTO.getId();
		this.email = userDTO.getEmail();
		this.userName = userDTO.getUserName();
		this.firstName = userDTO.getFirstName();
		this.lastName = userDTO.getLastName();
		this.password = userDTO.getPassword();
		this.roles = userDTO.getRoles();
		this.mobileNumber = userDTO.getMobileNumber();
    }

    public UserDTO toUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(this.id);
		userDTO.setEmail(this.email);
		userDTO.setUserName(this.userName);
		userDTO.setFirstName(this.firstName);
		userDTO.setLastName(this.lastName);
	
		userDTO.setRoles(this.roles);
		userDTO.setMobileNumber(this.mobileNumber);
		userDTO.setStoreName(this.storeName);
		return userDTO;
    }

    public Set<UserRole> getRoles() {
    	return roles;
    }

    public void setRoles(Set<UserRole> roles) {
    	this.roles = roles;
    }

    @Override
    public String toString() {
    	return "UserEntity [id=" + id + ", email=" + email + ", userName=" + userName + ", firstName=" + firstName
		+ ", lastName=" + lastName + ", mobileNumber=" + mobileNumber + ", roles=" + roles + "]";
    }

}
