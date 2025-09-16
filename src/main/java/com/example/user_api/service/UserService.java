package com.example.user_api.service;

import com.example.user_api.dto.PhoneDto;
import com.example.user_api.dto.UserDetailsResponse;
import com.example.user_api.dto.UserRequest;
import com.example.user_api.dto.UserResponse;
import com.example.user_api.exception.EmailAlreadyExistsException;
import com.example.user_api.exception.UserNotFoundException;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import com.example.user_api.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenGenerator tokenGenerator;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Check if email already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new EmailAlreadyExistsException("El correo ya registrado: " + userRequest.getEmail());
        }

    
        // Create user with JWT token and phones
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getEmail())
                .password(userRequest.getPassword())
                .token(tokenGenerator.generateToken(userRequest.getEmail()))
                .build();

        // Add phones to user
        if (userRequest.getPhones() != null && !userRequest.getPhones().isEmpty()) {
            for (PhoneDto phoneDto : userRequest.getPhones()) {
                User.Phone phone = User.Phone.builder()
                        .number(phoneDto.getNumber())
                        .cityCode(phoneDto.getCityCode())
                        .countryCode(phoneDto.getCountryCode())
                        .user(user)
                        .build();
                user.addPhone(phone);
            }
        }

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDetailsResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado con ID: " + id));
        return UserDetailsResponse.fromEntity(user);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.isActive())
                .build();
    }
}
