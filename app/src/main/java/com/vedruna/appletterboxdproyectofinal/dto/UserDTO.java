package com.vedruna.appletterboxdproyectofinal.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserDTO implements Serializable {
    private Long userId;
    private String username;
    private LocalDateTime createDate;

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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
