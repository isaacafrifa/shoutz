package com.iam.shoutz.service;

import com.iam.shoutz.entity.Post;
import com.iam.shoutz.entity.User;
import com.iam.shoutz.exception.ResourceNotFound;
import com.iam.shoutz.repository.PostRepository;
import com.iam.shoutz.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record PostService(PostRepository postRepository, UserRepository userRepository) {

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByUser(Long userId){
        //Get user
        var foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        //Get all posts belonging to user
        return foundUser.getPosts();
    }


}
