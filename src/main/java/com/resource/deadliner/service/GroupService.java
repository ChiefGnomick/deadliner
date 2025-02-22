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

    public boolean changeUserGroup(String tgId, String newGroupName) {
        Optional<User> userOptional = userRepository.findByTgId(tgId);
        Optional<Group> groupOptional = groupRepository.findByGroupNumber(newGroupName);

        if (userOptional.isPresent() && groupOptional.isPresent()) {
            User user = userOptional.get();
            Group newGroup = groupOptional.get();
            user.setGroup(newGroup);
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        } else if (userOptional.isPresent() && ! groupOptional.isPresent()) {
            if (ScheduleParser.ParseWeek(newGroupName, "1") == null) {
                return false;
            }
            else {
                User user = userOptional.get();
                Group newGroup = new Group();
                newGroup.setGroupNumber(newGroupName);
                user.setGroup(newGroup);
                user.setRole(Role.USER);
                groupRepository.save(newGroup);
                userRepository.save(user);
                return true;
            }
        } else {
            return false;
        }
    }
}