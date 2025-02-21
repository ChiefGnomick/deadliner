package com.resource.deadliner.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.resource.deadliner.model.Group;
import com.resource.deadliner.model.Role;
import com.resource.deadliner.model.User;
import com.resource.deadliner.repository.GroupRepository;
import com.resource.deadliner.repository.UserRepository;

@Service
public class GroupService {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public boolean changeUserGroup(Long userId, Long groupId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);

        if (userOptional.isPresent() && groupOptional.isPresent()) {
            User user = userOptional.get();
            Group newGroup = groupOptional.get();
            user.setGroup(newGroup);
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}