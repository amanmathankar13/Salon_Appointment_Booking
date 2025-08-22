package com.sab.category_service.service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.sab.category_service.payload.dto.UserDTO;



@FeignClient("USERSERVICE")
public interface UserFeignClient {

    @GetMapping("/user/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws Exception;

    @GetMapping("/user/profile")
	public ResponseEntity<UserDTO> getUserFromJwtToken(
			@RequestHeader("Authorization") String jwt) throws Exception;
}
