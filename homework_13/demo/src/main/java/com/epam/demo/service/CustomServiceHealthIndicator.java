package com.epam.demo.service;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomServiceHealthIndicator extends AbstractHealthIndicator {
    private final String projectName = "Spring Boot Test Project Application";

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up().withDetail("Name of project", projectName).build();
    }
}
