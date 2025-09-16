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
    private String number;
    
    @NotBlank(message = "{\"mensaje\": \"El código de ciudad es requerido\"}")
    @JsonProperty("citycode")
    private String cityCode;
    
    @NotBlank(message = "{\"mensaje\": \"El código de país es requerido\"}")
    @JsonProperty("contrycode")
    private String countryCode;
}
