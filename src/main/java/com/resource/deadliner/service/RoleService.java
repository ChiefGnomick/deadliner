package com.resource.deadliner.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.resource.deadliner.model.Role;
import com.resource.deadliner.model.User;
import com.resource.deadliner.repository.UserRepository;

@Service
public class RoleService {

    private final UserRepository userRepository;

    public RoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean changeUserRole(String requesterTgId, String targetTgId, Role newRole) {
        Optional<User> requesterOptional = userRepository.findByTgId(requesterTgId);
        Optional<User> targetUserOptional = userRepository.findByTgId(targetTgId);

        if (requesterOptional.isPresent() && targetUserOptional.isPresent()) {
            User requester = requesterOptional.get();
            User targetUser = targetUserOptional.get();

            if (!canChangeRole(requester, newRole)) {
                return false; 
            }

            targetUser.setRole(newRole);
            userRepository.save(targetUser);
            return true;
        }
        return false;
    }

    private boolean canChangeRole(User requester, Role newRole) {
        Role currentRole = requester.getRole();

        switch (newRole) {
            case Role.MODERATOR:
                return currentRole.equals(Role.ADMIN) || currentRole.equals(Role.SUPER_ADMIN);
            case Role.ADMIN:
                return currentRole.equals(Role.SUPER_ADMIN);
            case Role.SUPER_ADMIN:
                return false;
            default:
                return true;
        }
    }

    public boolean requestSuperAdminUpgrade(String requesterTgId, String confirmationCode) {
        Optional<User> requesterOptional = userRepository.findByTgId(requesterTgId);

        if (requesterOptional.isPresent()) {
            User requester = requesterOptional.get();
            if ("SECRET_CODE_52".equals(confirmationCode)) {
                requester.setRole(Role.SUPER_ADMIN);
                userRepository.save(requester);
                return true;
            }
        }
        return false;
    }
}