package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.service.GroupService;;

@RestController
@RequestMapping("/api/group")
public class GroupController {
    
    private final GroupService groupService;
    
    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/change-group")
    public ResponseEntity<String> changeUserGroup(@RequestParam Long userId, @RequestParam Long groupId) {
        boolean success = groupService.changeUserGroup(userId, groupId);
        if (success) {
            return ResponseEntity.ok("Группа успешно изменена, роль установлена в 'user'");
        } else {
            return ResponseEntity.badRequest().body("Ошибка при смене группы");
        }
    } 
}
