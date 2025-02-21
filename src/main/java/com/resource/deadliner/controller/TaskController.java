package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.dto.PersonalTaskRequest;
import com.resource.deadliner.dto.TaskRequest;
import com.resource.deadliner.model.PersonalTask;
import com.resource.deadliner.model.Task;
import com.resource.deadliner.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }

    @PostMapping("/add-personal")
    public ResponseEntity<PersonalTask> addPersonalTask(@RequestBody PersonalTaskRequest request) {
        return ResponseEntity.ok(taskService.createPersonalTask(request));
    }
}
