package com.iam.shoutz.controller;

import com.iam.shoutz.entity.Post;
import com.iam.shoutz.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("api/v1")
@Slf4j
@Tag(name = "Post", description = "the Post endpoints")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @Operation(summary = "Get all posts", description = "Providing all posts in the system", tags = {})
    @GetMapping("/posts")
    public List<Post> allPosts() {
        log.info("Get Mapping for all posts invoked");
        return postService.getAllPosts();
    }


    @Operation(summary = "Get all posts of user",
            description = "Get all the posts of a user by using user's id",
            tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    @GetMapping("/users/{id}/posts")
    public CollectionModel<Post> getPostsForUser(@PathVariable("id") Long id) {
        log.info("Get Mapping for all posts of a user invoked");
        return CollectionModel.of(postService.getPostsByUser(id));
    }
}
