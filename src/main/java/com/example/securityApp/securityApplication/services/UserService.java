package com.example.securityApp.securityApplication.services;

import com.example.securityApp.securityApplication.exceptions.ResourceNotFoundException;
import com.example.securityApp.securityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found with email " + username));
    }
}
