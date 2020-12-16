package com.epam.springcloud.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping
@RestController
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping
    public Set<Notification> getAllProducts() {
        return notificationRepository.getAllNotifications();
    }

    @PostMapping
    public ResponseEntity<JsonNode> createNotification(@RequestBody String userName) throws JsonProcessingException {
        notificationRepository.addNotify(userName);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree("{\"status\": \"ok\"}");
        return ResponseEntity.ok(json);
    }

}
