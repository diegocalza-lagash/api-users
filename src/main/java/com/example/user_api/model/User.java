package com.example.user_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "created", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();
    
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    
    @Column(name = "modified")
    private LocalDateTime modified;
    
    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private boolean isActive = true;
    
    private String token;
    
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.created = now;
        this.modified = now;
        this.lastLogin = now;
        this.isActive = true;
    
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.modified = LocalDateTime.now();
    }
    

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity
    @Table(name = "user_phones")
    public static class Phone {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;
        
        @Column(nullable = false, length = 20)
        private String number;
        
        @Column(name = "city_code", nullable = false, length = 10)
        private String cityCode;
        
        @Column(name = "country_code", nullable = false, length = 10)
        private String countryCode;
    }
}
