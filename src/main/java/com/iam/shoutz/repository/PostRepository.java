package com.iam.shoutz.repository;

import com.iam.shoutz.entity.Post;
import com.iam.shoutz.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findPostByUserAndPostId(User user, Long postId);
}
