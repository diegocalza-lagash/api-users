package com.example.user_api.dto;

import com.example.user_api.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDetailsResponse extends UserResponse {
    private List<PhoneDto> phones;

    public static UserDetailsResponse fromEntity(User user) {
        UserDetailsResponse response = UserDetailsResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .phones(user.getPhones().stream()
                        .map(phone -> PhoneDto.builder()
                                .number(phone.getNumber())
                                .cityCode(phone.getCityCode())
                                .countryCode(phone.getCountryCode())
                                .build())
                        .collect(Collectors.toList()))
                .build();
                
        return response;
    }
}
