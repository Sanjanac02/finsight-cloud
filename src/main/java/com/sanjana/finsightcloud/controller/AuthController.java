package com.sanjana.finsightcloud.controller;

import com.sanjana.finsightcloud.dto.UserRequest;
import com.sanjana.finsightcloud.dto.UserResponse;
import com.sanjana.finsightcloud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.sanjana.finsightcloud.dto.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse registerUser(@Valid @RequestBody UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

}
