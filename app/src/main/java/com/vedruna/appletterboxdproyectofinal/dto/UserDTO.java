package com.vedruna.appletterboxdproyectofinal.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Long userId;
    private String username;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
