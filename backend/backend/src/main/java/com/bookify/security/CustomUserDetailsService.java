package com.bookify.security;

import com.bookify.entity.AppRole;
import com.bookify.entity.Profile;
import com.bookify.entity.UserRole;
import com.bookify.repository.ProfileRepository;
import com.bookify.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<UserRole> userRoles = userRoleRepository.findByUser(profile);
        List<GrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name()))
                .collect(Collectors.toList());

        return User.builder()
                .username(profile.getEmail())
                .password("") // We don't store passwords, using JWT
                .authorities(authorities)
                .build();
    }

    public List<AppRole> getUserRoles(Profile profile) {
        List<UserRole> userRoles = userRoleRepository.findByUser(profile);
        return userRoles.stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList());
    }
}
