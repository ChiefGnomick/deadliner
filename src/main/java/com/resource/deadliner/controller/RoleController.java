package com.resource.deadliner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.dto.RoleChangeRequest;
import com.resource.deadliner.dto.SuperAdminRequest;
import com.resource.deadliner.model.Role;
import com.resource.deadliner.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PutMapping("/change")
    public ResponseEntity<String> changeUserRole(@RequestBody RoleChangeRequest request) {
        boolean success = roleService.changeUserRole(request.getRequesterTgId(), request.getTargetTgId(), Role.valueOf(request.getNewRole()));
        
        if (success) {
            return ResponseEntity.ok("Роль успешно изменена.");
        } else {
            return ResponseEntity.badRequest().body("Ошибка: недостаточно прав.");
        }
    }

    @PutMapping("/request-super-admin")
    public ResponseEntity<String> requestSuperAdmin(@RequestBody SuperAdminRequest request) {
        boolean success = roleService.requestSuperAdminUpgrade(request.getRequesterTgId(), request.getConfirmationCode());
        if (success) {
            return ResponseEntity.ok("Роль 'super_admin' успешно назначена.");
        } else {
            return ResponseEntity.badRequest().body("Ошибка: неверный код подтверждения или недостаточно прав.");
        }
    }
}