package com.iam.shoutz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoutzApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoutzApplication.class, args);
	}

}
