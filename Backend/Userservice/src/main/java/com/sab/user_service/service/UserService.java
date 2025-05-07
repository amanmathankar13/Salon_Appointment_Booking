package com.sab.user_service.service;

import java.util.List;

import com.sab.user_service.entity.User;
import com.sab.user_service.exception.UserException;

public interface UserService {
    
    User createUser(User user);
    User getUserById(Long id) throws UserException;
    User updateUser(User user, Long id) throws UserException;
    String deleteUser(Long id) throws UserException;
    List<User> getAllUsers();
}
