package com.iam.shoutz.service;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.exception.ResourceNotFound;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* Bare-minimum usersService  */
@Component
public class UserService {

    private static List<User> users = new ArrayList<>();
    private static long counter=0L;
    static {
        users.add(new User(++counter, "adam1", "Adam", "Freeman", LocalDate.now()));
        users.add(new User(++counter, "chris_manor", "Christopher", "Manor", LocalDate.now()));
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(Long id){
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFound("User not found"));
    }

    public User createUser(User user){
        user.setId(++counter);
        user.setCreatedOn(LocalDate.now());
        users.add(user);
        return user;
    }

    public void deleteUser(Long id){
        User foundUser= this.getUserById(id); // catching user not found here :)
        users.removeIf(user -> user.getId().equals(foundUser.getId()));
    }
}
