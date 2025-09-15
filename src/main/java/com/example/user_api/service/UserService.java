package com.example.user_api.service;

import com.example.user_api.dto.UserRequest;
import com.example.user_api.dto.UserResponse;
import com.example.user_api.exception.EmailAlreadyExistsException;
import com.example.user_api.model.User;
import com.example.user_api.repository.UserRepository;
import com.example.user_api.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    
        // Create user with JWT token
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .username(userRequest.getEmail())
                .password(userRequest.getPassword())
                .token(tokenGenerator.generateToken(userRequest.getEmail()))
                .build();

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .created(user.getCreated())
                .modified(user.getModified())
                .lastLogin(user.getLastLogin())
                .isActive(user.isActive())
                .token(user.getToken())
                .build();
    }
}
