package com.iam.shoutz.controller;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@Slf4j

@Tag(name = "User", description = "the User endpoints") //OpenApi annotation
public record UserController(UserService userService) {


    @Operation(summary = "Get all users", description = "", tags = {})
    @GetMapping("/users")
    public List<User> allUsers() {
        log.info("Get Mapping for all users invoked");
        return userService.getAllUsers();
    }

    @Operation(summary = "Find user by id",
            description = "",
            tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable("id") Long id) {
        log.info("Get Mapping for single user invoked");
        return userService.getUserById(id);
    }

    @Operation(
            summary = "Create a new user",
            description = "Use this endpoint to create a new user",
            tags = {},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "409", description = "User already created")
            }
    )
    @PostMapping("/users")
    public ResponseEntity<User> postUser(@Valid @RequestBody User dirtyUser) {
        User savedUser = userService.createUser(dirtyUser);
        log.info("Post Mapping for user invoked using {}", savedUser.getUsername());
        //add resource location URI as a response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //get currentURI i.e."/users"
                .path("/{id}") //append "/id"
                .buildAndExpand(savedUser.getId()) // replace {id} with user's id
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Deletes an existing user"
            , description = ""
            , tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found") })
    @DeleteMapping("/users/{id}")
    public void deleteOneUser(@PathVariable("id") Long id) {
        log.info("Delete Mapping for single user invoked");
        userService.deleteUser(id);
    }


}
