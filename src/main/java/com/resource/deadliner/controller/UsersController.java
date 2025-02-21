package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.dto.AddUserRequest;
import com.resource.deadliner.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody AddUserRequest request) {
        boolean success = userService.addUser(request.getTgId(), request.getGroupNumber());
        if (success) {
            return ResponseEntity.ok("Пользователь успешно добавлен");
        }
        else {
            return ResponseEntity.badRequest().body("Ошибка: пользователь уже есть или не существует такой группы");
        }
    }
}
