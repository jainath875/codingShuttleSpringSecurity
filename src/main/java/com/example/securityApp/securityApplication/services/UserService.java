package com.example.securityApp.securityApplication.services;

import com.example.securityApp.securityApplication.dto.LoginDto;
import com.example.securityApp.securityApplication.dto.SignupDto;
import com.example.securityApp.securityApplication.dto.UserDto;
import com.example.securityApp.securityApplication.entity.User;
import com.example.securityApp.securityApplication.exceptions.ResourceNotFoundException;
import com.example.securityApp.securityApplication.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Username not found with email " + username));
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public UserDto signup(SignupDto signupDto) {
        boolean status = userRepository.existsByEmail(signupDto.getEmail());

        if(status){
            throw new BadCredentialsException("Email already exists");
        }

        User toBeCreatedUser = modelMapper.map(signupDto, User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        User savedUser = userRepository.save(toBeCreatedUser);

        return modelMapper.map(savedUser,  UserDto.class);
    }


}
