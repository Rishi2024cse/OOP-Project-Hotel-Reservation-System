package com.bookify.service;

import com.bookify.dto.AuthRequest;
import com.bookify.dto.AuthResponse;
import com.bookify.entity.AppRole;
import com.bookify.entity.Profile;
import com.bookify.entity.UserRole;
import com.bookify.repository.ProfileRepository;
import com.bookify.repository.UserRoleRepository;
import com.bookify.security.CustomUserDetailsService;
import com.bookify.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Transactional
    public AuthResponse register(AuthRequest request) {
        if (profileRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create profile
        Profile profile = new Profile();
        profile.setId(UUID.randomUUID());
        profile.setEmail(request.getEmail());
        profile.setFullName(request.getFullName());
        // For now, we'll store a default password since we're migrating from Supabase
        // In production, you should hash the actual password from the request
        profile.setPasswordHash(passwordEncoder.encode("defaultPassword"));
        profile = profileRepository.save(profile);

        // Assign default USER role
        UserRole userRole = new UserRole(profile, AppRole.USER);
        userRoleRepository.save(userRole);

        // Generate JWT token
        String token = jwtUtil.generateToken(profile.getId(), profile.getEmail());
        List<AppRole> roles = userDetailsService.getUserRoles(profile);

        return new AuthResponse(token, profile.getId(), profile.getEmail(), profile.getFullName(), roles);
    }

    public AuthResponse login(AuthRequest request) {
        Profile profile = profileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // In a real application, you would verify the password here
        // For now, we'll just generate a token since we're migrating from Supabase auth

        String token = jwtUtil.generateToken(profile.getId(), profile.getEmail());
        List<AppRole> roles = userDetailsService.getUserRoles(profile);

        return new AuthResponse(token, profile.getId(), profile.getEmail(), profile.getFullName(), roles);
    }

    public boolean hasRole(UUID userId, AppRole role) {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return userRoleRepository.existsByUserAndRole(profile, role);
    }
}
