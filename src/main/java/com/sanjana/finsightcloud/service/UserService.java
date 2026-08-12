package com.sanjana.finsightcloud.service;

import com.sanjana.finsightcloud.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.sanjana.finsightcloud.dto.UserRequest;
import com.sanjana.finsightcloud.entity.User;
import com.sanjana.finsightcloud.exception.EmailAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sanjana.finsightcloud.dto.UserResponse;
import com.sanjana.finsightcloud.dto.LoginRequest;
import com.sanjana.finsightcloud.dto.LoginResponse;
import com.sanjana.finsightcloud.exception.InvalidCredentialsException;
import com.sanjana.finsightcloud.service.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
                    UserRepository userRepository, 
                    BCryptPasswordEncoder passwordEncoder,
                    JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(UserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                 "Email is already registered"
            );
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }


    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid email or password"
                ));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return response;
    }

}
