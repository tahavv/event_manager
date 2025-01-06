package com.eventmanager.eventmanager.services;

import com.eventmanager.eventmanager.dto.UserDTO;
import com.eventmanager.eventmanager.model.User;
import com.eventmanager.eventmanager.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    // Create a User
    public User createUser(UserDTO userDTO)  {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setDob(userDTO.getDob());
        user.setRole(userDTO.getRole());
        user.setAccountLocked(userDTO.isAccountLocked());
        try {
            emailService.sendWelcomeEmail(userDTO.getEmail(),userDTO.getName(),userDTO.getPassword());
            user.setVerified(true);
        }catch (Exception e){
            System.out.println("Error sending welcome email :"+e.getMessage());
            user.setVerified(false);
        }
        return userRepository.save(user);
    }

    // Get all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get User by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update a User
    public Optional<User> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(user -> {
                    if (userDTO.getName() != null) {
                        user.setName(userDTO.getName());
                    }
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail());
                    }
                    if (userDTO.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                    }
                    if (userDTO.getDob() != null) {
                        user.setDob(userDTO.getDob());
                    }
                    if(userDTO.getRole() != null) {
                        user.setRole(userDTO.getRole());
                    }
                    user.setAccountLocked(userDTO.isAccountLocked());
                    return userRepository.save(user);
                });
    }

    // Delete a User
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
