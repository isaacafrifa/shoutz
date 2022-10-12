package com.iam.shoutz.controller;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@Slf4j
public record UserController(UserService userService) {

    @GetMapping("/users")
    public List<User> allUsers(){
        log.info("Get Mapping for all users invoked");
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable("id") Long id){
        log.info("Get Mapping for single user invoked");
        return userService.getUserById(id);
    }
}
