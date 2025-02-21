package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.dto.TaskResponse;
import com.resource.deadliner.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<TaskResponse> getUserTasks(@RequestParam String tgId) {
        TaskResponse response = notificationService.getUserTasks(tgId);

        if (response != null) {
            return ResponseEntity.ok(response); 
        } else {
            return ResponseEntity.status(404).body(null); 
        }
    }
}