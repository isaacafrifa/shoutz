package com.iam.shoutz.service;

import com.iam.shoutz.config.AppConfig;
import com.iam.shoutz.entity.Post;
import com.iam.shoutz.entity.User;
import com.iam.shoutz.repository.PostRepository;
import com.iam.shoutz.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public record DBLoader(UserRepository userRepository, PostRepository postRepository, AppConfig appConfig) implements CommandLineRunner {

    @Override
    public void run(String... args) {
        init();
    }

    public void init() {
         User user1 = new User(null, "yawmens", "Yaw", "Afrifa", appConfig().getDefaultProfileImageUrl(),
                30, null, null);
        User user2 = new User(null, "miss_selim", "Selim", "VanLare", appConfig().getDefaultProfileImageUrl(),
                35, null, null);

        Post post1 = new Post(null, "This is my first post",null, null, user1);
        Post post2 = new Post(null, "This is my second post",null, null, user1);
        Post post3 = new Post(null, "I'm refreshing my Microservices knowledge",null, null, user2);

        var users = Arrays.asList(user1, user2);
        var posts = Arrays.asList(post1, post2, post3);
        userRepository.saveAll(users);
        postRepository.saveAll(posts);
    }


}
