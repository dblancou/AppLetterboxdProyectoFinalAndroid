package com.vedruna.appletterboxdproyectofinal.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private Long userId;
    private String username;
    private LocalDateTime createDate;

    public User() {}

    public User(Long userId, String username, LocalDateTime createDate) {
        this.userId = userId;
        this.username = username;
        this.createDate = createDate;
    }

    // Getters and setters

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
