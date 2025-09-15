package com.example.user_api.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;



@Data
public class UserRequest {
    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,100}$", message = "Name must be between 3 and 100 characters and contain only letters and spaces")
    private String name;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
    
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=(?:[^0-9]*[0-9]){2})[a-zA-Z0-9]{8,}$", 
             message = "Password must be at least 8 characters long, contain at least one uppercase letter, lowercase letters, and exactly two numbers")
    private String password;
    
 
}
