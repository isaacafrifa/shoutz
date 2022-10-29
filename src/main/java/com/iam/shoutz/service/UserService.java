package com.iam.shoutz.service;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.exception.ResourceAlreadyExists;
import com.iam.shoutz.exception.ResourceNotFound;
import com.iam.shoutz.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public record UserService (UserRepository userRepository){

    public List<User> getAllUsers() {
        var allUsers = userRepository.findAll();
        return allUsers;
    }

    public User getUserById(Long id){
        return this.getAllUsers().stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    public User createUser(User user){
        if (userRepository.existsByUsername(user.getUsername())){
            throw new ResourceAlreadyExists("User already exists");
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        User foundUser= this.getUserById(id); // catching user not found here :)
        userRepository.delete(foundUser);
    }
}
