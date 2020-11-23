package com.epam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.*")
public class SpringBootTestProjectApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootTestProjectApplication.class, args);
    }

}
