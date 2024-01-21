package com.iam.shoutz.controller;

import com.iam.shoutz.entity.Post;
import com.iam.shoutz.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/users/{userId}/posts")
    public CollectionModel<Post> getPostsForUser(@PathVariable("userId") Long userId) {
        log.info("Get Mapping for all posts of a user invoked");
        return CollectionModel.of(postService.getPostsByUser(userId));
    }


    @Operation(summary = "Get single post of user",
            description = "Get a single post of a user by using user's id",
            tags = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Resource not found")})
    @GetMapping("/users/{userId}/posts/{postId}")
    public EntityModel<Post> getSinglePostForUser(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId) {
        var foundPost = postService.getOnePostByUserIdAndPostId(userId, postId);
        log.info("Get Mapping for a single post of user invoked. [User = {}]", foundPost.getUser().getUsername());
//        return EntityModel.of(postService.getPostsByUser(userId));
        Link allUserPostsLink = linkTo(methodOn(this.getClass()).getPostsForUser(foundPost.getUser().getUserId()))
                .withRel("all-user-posts");
        return EntityModel.of(foundPost,
                allUserPostsLink);
    }


    @Operation(
            summary = "Create a new post for user",
            description = "Use this endpoint to create a new post for a particular user using the user's id",
            tags = {},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Post created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "409", description = "Post already created")
            }
    )
    @PostMapping("/users/{userId}/posts")
    public ResponseEntity<Object> createPostForUser(@PathVariable Long userId,@Valid @RequestBody Post dirtyPost) {

        var savedPost = postService.createPost(userId, dirtyPost);
        log.info("Post Mapping for creating user post invoked. [User = {}]", savedPost.getUser().getUsername());
        // add resource location URI as a response header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest() //get currentURI i.e."/users"
                .path("/{postId}") //append "/postId"
                .buildAndExpand(savedPost.getPostId()) // replace {postId} with post's id
                .toUri();
        // add hateoas self and all-user-posts links to output
        Links links = Links.of(
                linkTo(methodOn(this.getClass()).getPostsForUser(savedPost.getUser().getUserId()))
                        .slash(savedPost.getPostId())
                        .withSelfRel(),
                linkTo(methodOn(this.getClass()).getPostsForUser(savedPost.getUser().getUserId()))
                        .withRel("all-user-posts"));
        EntityModel<Post> entityModel = EntityModel.of(savedPost,
                links);
        return ResponseEntity.created(location).body(entityModel);
    }



}
