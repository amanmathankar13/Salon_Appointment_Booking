package com.sab.user_service.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.sab.user_service.payload.dto.Credentials;
import com.sab.user_service.payload.dto.KeyCloakRole;
import com.sab.user_service.payload.dto.KeyCloakUserDTO;
import com.sab.user_service.payload.dto.SignUpDTO;
import com.sab.user_service.payload.dto.UserRequest;
import com.sab.user_service.payload.response.TokenResponse;

@Service
public class KeyCloakService {


    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String KEYCLOAK_ADMIN_API = KEYCLOAK_BASE_URL+"/admin/realms/master/users";
    private static final String TOKEN_URL = KEYCLOAK_BASE_URL + "/realms/master/protocol/openid-connect/token";
    private static final String KEYCLOAK_REALM = "master";
    private static final String KEYCLOAK_CLIENT_ID = "salon-booking-client";
    private static final String KEYCLOAK_CLIENT_SECRET = "wIIjnnv7gncck1MP1SxKqWlwdl6A6F3N";
    private static final String KEYCLOAK_GRANT_TYPE = "password";
    private static final String scope = "openid email profile";
    private static final String KEYCLOAK_USERNAME = "admin@gmail.com";
    private static final String KEYCLOAK_PASSWORD = "admin";
    private static final String clientid = "af86555d-1e67-41e7-bcef-8220b6ef640b";


    @Autowired
    private RestTemplate restTemplate;

    public void createUser(SignUpDTO signUpDTO) throws Exception{

        String ACCESS_TOKEN = getAdminAccessToken(KEYCLOAK_USERNAME, KEYCLOAK_PASSWORD, KEYCLOAK_GRANT_TYPE, null).getAccessToken();

        Credentials credentials = new Credentials();
        credentials.setTemporary(false);
        credentials.setType("password");
        credentials.setValue(signUpDTO.getPassword());

        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(signUpDTO.getUsername());
        userRequest.setEmail(signUpDTO.getEmail());
        userRequest.setLastName(signUpDTO.getFullName());
        userRequest.setEnabled(true);
        userRequest.getCredentials().add(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(ACCESS_TOKEN);

        HttpEntity<UserRequest> requesHttpEntity = new HttpEntity<>(userRequest,headers);
        ResponseEntity<String> response = restTemplate.exchange(KEYCLOAK_ADMIN_API, HttpMethod.POST, requesHttpEntity, String.class);
        if(response.getStatusCode()==HttpStatus.CREATED){
            System.out.println("User created successfully");
            KeyCloakUserDTO user = fetchFirstUserByUsername(signUpDTO.getUsername(), ACCESS_TOKEN);
            KeyCloakRole role = getRoleByName(clientid, ACCESS_TOKEN, signUpDTO.getRole().toString());
            List<KeyCloakRole> roles = new ArrayList<>();
            roles.add(role);
            assignRoleToUser(user.getId(), clientid, roles, ACCESS_TOKEN);
        }
        else{
            System.out.println("Failed to create user");
            throw new Exception(response.getBody());
        }
    }


    public TokenResponse getAdminAccessToken(String username, String password, String grantType, String refreshToken) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("username", username);
        requestBody.add("password", password);
        requestBody.add("refresh_token", refreshToken);
        requestBody.add("client_id", KEYCLOAK_CLIENT_ID);
        requestBody.add("client_secret", KEYCLOAK_CLIENT_SECRET);
        requestBody.add("scope", scope);
        HttpEntity<MultiValueMap<String,String>>  requesHttpEntity = new HttpEntity<>(requestBody,headers);
        ResponseEntity<TokenResponse> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requesHttpEntity, TokenResponse.class);
        if(response.getStatusCode()==HttpStatus.OK && response.getBody()!=null){
            return response.getBody();
        }
        throw new Exception("Failed to obtain access token");
    }

    public KeyCloakRole getRoleByName(String clientId, String token, String role) throws Exception{

        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/clients/"+clientId+"/roles/"+role;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
       
        HttpEntity<Void>  requesHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<KeyCloakRole> response = restTemplate.exchange(url, HttpMethod.GET, requesHttpEntity, KeyCloakRole.class);
        
        return response.getBody();
    }

    public KeyCloakUserDTO fetchFirstUserByUsername(String username, String token) throws Exception{
        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users?username="+username;
 
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
       
        HttpEntity<String>  requesHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<KeyCloakUserDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, requesHttpEntity, KeyCloakUserDTO[].class);
        KeyCloakUserDTO[] users = response.getBody();
        if(users.length>0){
            return users[0];
        }
        throw new Exception("user not found by username" + username);
    }

    public void assignRoleToUser(String userId, String clientId, List<KeyCloakRole> roles, String token) throws Exception{
        String url = KEYCLOAK_BASE_URL + "/admin/realms/master/users/"+userId+"/role-mappings/clients/ "+clientId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<KeyCloakRole>>  requesHttpEntity = new HttpEntity<>(roles, headers);
        try{
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requesHttpEntity, String.class);
        }
        catch(Exception e){
            throw new Exception("Failed to assign role" + e.getMessage());
        }
    }
}

