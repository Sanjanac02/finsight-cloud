package com.sanjana.finsightcloud.service;

import org.springframework.stereotype.Service;

import com.sanjana.finsightcloud.controller.HelloController;

@Service
public class HelloService {
    public String getGreeting() {
        return "Hello! This is Sanjana...";
    }
}
