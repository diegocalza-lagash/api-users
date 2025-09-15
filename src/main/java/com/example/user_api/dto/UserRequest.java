package com.example.user_api.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Data;



@Data
public class UserRequest {
    @NotBlank(message = "{\"mensaje\": \"El nombre es requerido\"}")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,100}$", 
             message = "{\"mensaje\": \"El nombre debe tener entre 3 y 100 caracteres y contener solo letras y espacios\"}")
    private String name;
    
    @NotBlank(message = "{\"mensaje\": \"El correo electrónico es requerido\"}")
    @Email(message = "{\"mensaje\": \"El formato del correo electrónico no es válido\"}")
    @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", 
             message = "{\"mensaje\": \"El formato del correo electrónico no es válido. Debe ser: ejemplo@dominio.com\"}")
    private String email;
    
    @NotBlank(message = "{\"mensaje\": \"La contraseña es requerida\"}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=(.*[0-9]){2,})[a-zA-Z0-9]*$", 
             message = "{\"mensaje\": \"La contraseña debe contener al menos una mayúscula, minúsculas y al menos dos números\"}")
    private String password;
    
 
}
