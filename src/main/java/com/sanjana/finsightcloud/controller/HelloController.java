package com.sanjana.finsightcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjana.finsightcloud.service.HelloService;

@RestController
public class HelloController {
    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }
    
    @GetMapping("/hello") 
    public String hello() {
        return helloService.getGreeting();
    }
}
