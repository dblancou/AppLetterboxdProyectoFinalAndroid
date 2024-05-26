package com.vedruna.appletterboxdproyectofinal.repository;

import com.vedruna.appletterboxdproyectofinal.models.User;
import java.util.List;

public interface UserRepository {
    void createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(Long id);
    User findByUsername(String username);
    void followUser(String username, Long followUserId);
    void unfollowUser(String username, Long unfollowUserId);
    List<User> getFollowers(String username);
    List<User> getFollows(String username);
}
