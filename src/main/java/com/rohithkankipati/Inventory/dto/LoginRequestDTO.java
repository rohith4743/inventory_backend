package com.rohithkankipati.Inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginRequestDTO {

	@NotBlank(message = "{username.required}")
	@Pattern(regexp = "^[a-zA-Z0-9._-]{3,}$", message = "{username.pattern}")
    private String username;

    @NotBlank(message = "{password.required}")
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
