package com.example.springboot;

import com.example.springboot.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class PasswordApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasswordApplication.class, args);
    }

}
