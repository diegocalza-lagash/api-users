package com.example.user_api.controller;

import com.example.user_api.dto.PhoneDto;
import com.example.user_api.dto.UserRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void registerUser_WithValidData_ShouldReturnCreated() throws Exception {
        UserRequest userRequest = createValidUserRequest();
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(userRequest.getName()))
                .andExpect(jsonPath("$.email").value(userRequest.getEmail()))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.is_active").isBoolean())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void registerUser_WithInvalidEmail_ShouldReturnBadRequest() throws Exception {
        UserRequest userRequest = createValidUserRequest();
        userRequest.setEmail("invalid-email");
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").value(containsString("El formato del correo electrónico")));
    }

    @Test
    public void registerUser_WithInvalidPassword_ShouldReturnBadRequest() throws Exception {
        UserRequest userRequest = createValidUserRequest();
        userRequest.setPassword("weak");
        
        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andReturn();
        
        
        String response = result.getResponse().getContentAsString();
        System.out.println("Response: " + response);
        
        JsonNode jsonNode = objectMapper.readTree(response);
        assert(jsonNode.has("mensaje"));
    }

    @Test
    public void registerUser_WithDuplicateEmail_ShouldReturnBadRequest() throws Exception {
        UserRequest userRequest = createValidUserRequest();
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)));
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    @Test
    public void registerUser_WithMissingRequiredFields_ShouldReturnBadRequest() throws Exception {
        UserRequest userRequest = new UserRequest();
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje").exists());
    }

    private UserRequest createValidUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("Validpass12");
        
        // Create phone DTO
        PhoneDto phone = PhoneDto.builder()
                .number("1234567")
                .cityCode("1")
                .countryCode("57")
                .build();
        
        // Set phones list
        userRequest.setPhones(List.of(phone));
        
        return userRequest;
    }
}
