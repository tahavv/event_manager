package com.eventmanager.eventmanager.controller;

import com.eventmanager.eventmanager.model.User;
import com.eventmanager.eventmanager.model.enmus.Role;
import com.eventmanager.eventmanager.repository.UserRepository;
import com.eventmanager.eventmanager.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class OAuth2SuccessHandler {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/api/auth/oauth2/success")
    public String oAuth2Success(OAuth2AuthenticationToken authentication) {
        // Extract user info from Google
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            // If not exists, create
            user = new User();
            user.setEmail(email);
            user.setName(name != null ? name : "Google User");
            user.setPassword("GOOGLE_OAUTH2");
            user.setRole(Role.USER);
            user.setVerified(true);
            userRepository.save(user);
        }

        // 2. Generate JWT
        String token = jwtTokenUtil.generateToken(email, user.getRole().name());

        // 3. Possibly redirect with token as query param, or store in cookie
        // e.g., "http://localhost:3000/oauth2?token=XYZ"
        return "redirect:http://localhost:3000/oauth2?token=" + token;
    }
}

