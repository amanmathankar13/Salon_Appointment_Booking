package com.sab.user_service.mapper;



import org.springframework.stereotype.Service;

import com.sab.user_service.entity.User;
import com.sab.user_service.payload.dto.UserDTO;

@Service
public class UserMapper {

    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getName());
        userDTO.setRole(user.getRole().toString());

        return userDTO;
    }
}
