package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
