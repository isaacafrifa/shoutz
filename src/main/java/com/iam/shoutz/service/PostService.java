package com.iam.shoutz.service;

import com.iam.shoutz.entity.Post;
import com.iam.shoutz.entity.User;
import com.iam.shoutz.exception.ResourceNotFound;
import com.iam.shoutz.repository.PostRepository;
import com.iam.shoutz.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record PostService(PostRepository postRepository, UserRepository userRepository) {

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // made this method to ensure DRY
    private User findUserByUserId(Long userId){
       return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    public List<Post> getPostsByUser(Long userId){
        //Get user
        var foundUser =this.findUserByUserId(userId);
        //Get all posts belonging to user
        return foundUser.getPosts();
    }

    public Post getOnePostByUserIdAndPostId(Long userId, Long postId){
        // Get user
        var foundUser = this.findUserByUserId(userId);
        // Get post
       return postRepository.findPostByUserAndPostId(foundUser, postId)
                .orElseThrow(()-> new ResourceNotFound("Post not found"));
    }

    public Post createPost(Long userId, Post unsavedPost){
        //Get user
        var foundUser =this.findUserByUserId(userId);
        // attach user to post
        unsavedPost.setUser(foundUser);
        //save post to repository
        return postRepository.save(unsavedPost);
    }

}
