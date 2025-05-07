package com.sab.user_service.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sab.user_service.entity.User;
import com.sab.user_service.exception.UserException;
import com.sab.user_service.repository.UserRepository;
import com.sab.user_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) throws UserException {
        return userRepository.findById(id).orElseThrow(()-> new UserException("user not found"));
    }

    @Override
    public User updateUser(User user, Long id) throws UserException {
        User existingUser = userRepository.findById(id).orElse(null); 
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setRole(user.getRole());
            existingUser.setUsername(user.getUsername());
            userRepository.save(existingUser);
            return existingUser;
        }
        throw new UserException("User Not Found");
    }

    @Override
    public String deleteUser(Long id) throws UserException {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return "User Deleted";
        }
        throw new UserException("User Not Found");
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
}
