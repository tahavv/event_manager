package com.eventmanager.eventmanager.services;

import com.eventmanager.eventmanager.model.User;
import com.eventmanager.eventmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // If user has role=ADMIN, we store "ROLE_ADMIN" internally, etc.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // in case we do manual auth
                .roles(user.getRole().toString()) // e.g. "ADMIN"
                .build();
    }
}
