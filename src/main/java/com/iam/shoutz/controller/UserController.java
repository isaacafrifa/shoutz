package com.iam.shoutz.controller;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/users")
    public ResponseEntity<User> postUser(@RequestBody User dirtyUser){
        User savedUser= userService.createUser(dirtyUser);
        log.info("Post Mapping for user invoked using {}", savedUser.getUsername());
        //add resource location URI as a response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //get currentURI i.e."/users"
                .path("/{id}") //append "/id"
                .buildAndExpand(savedUser.getId()) // replace {id} with user's id
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable("id") Long id){
        log.info("Delete Mapping for single user invoked");
        userService.deleteUser(id);
    }


}
