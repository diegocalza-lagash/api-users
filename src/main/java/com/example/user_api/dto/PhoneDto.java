package com.example.user_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    @NotBlank(message = "{\"mensaje\": \"El número de teléfono es requerido\"}")
    @Pattern(regexp = "^[0-9]{5,20}$", 
             message = "{\"mensaje\": \"El número de teléfono debe contener solo dígitos y tener entre 5 y 20 caracteres\"}")
    private String number;
    
    @NotBlank(message = "{\"mensaje\": \"El código de ciudad es requerido\"}")
    @Pattern(regexp = "^[0-9]{1,10}$", 
             message = "{\"mensaje\": \"El código de ciudad debe contener solo dígitos y tener hasta 10 caracteres\"}")
    @JsonProperty("citycode")
    private String cityCode;
    
    @NotBlank(message = "{\"mensaje\": \"El código de país es requerido\"}")
    @JsonProperty("contrycode")
    private String countryCode;
}
