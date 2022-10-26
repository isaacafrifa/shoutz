package com.iam.shoutz.service;

import com.iam.shoutz.entity.User;
import com.iam.shoutz.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

@Component
@Slf4j
public record DBLoader(UserRepository userRepository) implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        initUsers();
    }

    /* Persisting initial users */
    public void initUsers(){
        User user1 = new User(null,"yawmens","Yaw", "Afrifa",
                LocalDate.of(1992, Month.OCTOBER,5),null);
        User user2 = new User(null,"miss_selim","Selim", "VanLare",
                LocalDate.of(1992, Month.MARCH,23),null);
        var users= Arrays.asList(user1,user2);
        userRepository.saveAll(users);
    }
}
