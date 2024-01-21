package com.iam.shoutz.controller;

import com.iam.shoutz.dto.UserRequestDto;
import com.iam.shoutz.dto.UserResponseDto;
import com.iam.shoutz.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@Tag(name = "User", description = "the User endpoints") //OpenApi annotation
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Get all users", description = "", tags = {})
    @GetMapping
    public List<UserResponseDto> allUsers() {
        log.info("Get Mapping for all users invoked");
        return userService.getAllUsers();
    }


    @Operation(summary = "Find user by id",
            description = "",
            tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/{id}")
    public EntityModel<UserResponseDto> getOneUser(@PathVariable("id") Long id) {
        log.info("Get Mapping for single user invoked");
        Link allUsersLink = linkTo(methodOn(this.getClass()).allUsers())
                .withRel("all-users");
        return EntityModel.of(userService.getUserById(id),
                allUsersLink);
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
    @PostMapping
    public ResponseEntity<Object> postUser(@Valid @RequestBody UserRequestDto dirtyUser) {
        UserResponseDto savedUserResponseDto = userService.createUser(dirtyUser);
        log.info("Post Mapping for user invoked using {}", savedUserResponseDto.username());
        // add resource location URI as a response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //get currentURI i.e."/users"
                .path("/{id}") //append "/id"
                .buildAndExpand(savedUserResponseDto.userId()) // replace {id} with user's id
                .toUri();
        // add hateoas self and all-users links to output
        Links links = Links.of(
                linkTo(methodOn(this.getClass()).allUsers()).slash(savedUserResponseDto.userId())
                        .withSelfRel(),
                linkTo(methodOn(this.getClass()).allUsers())
                        .withRel("all-users"));
        EntityModel<UserResponseDto> entityModel = EntityModel.of(savedUserResponseDto,
                links);
        return ResponseEntity.created(location).body(entityModel);
    }


    @Operation(summary = "Deletes an existing user"
            , description = ""
            , tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @DeleteMapping("/{id}")
    public void deleteOneUser(@PathVariable("id") Long id) {
        log.info("Delete Mapping for single user invoked");
        userService.deleteUser(id);
    }


}
