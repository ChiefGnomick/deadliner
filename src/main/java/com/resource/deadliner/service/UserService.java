package com.resource.deadliner.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.resource.deadliner.model.Group;
import com.resource.deadliner.model.Role;
import com.resource.deadliner.model.User;
import com.resource.deadliner.repository.GroupRepository;
import com.resource.deadliner.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public UserService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public boolean addUser(String tgId, String groupNumber) {
        Optional<User> userOptional = userRepository.findByTgId(tgId);
        Optional<Group> groupOptional = groupRepository.findByGroupNumber(groupNumber);

        if (userOptional.isPresent()) {
            return false;
        }
        else if (!userOptional.isPresent() && groupOptional.isPresent()) {
            User newUser = new User();
            newUser.setGroup(groupOptional.get());
            newUser.setRole(Role.USER);
            newUser.setTgId(tgId);
            userRepository.save(newUser);
            return true;
        }
        else if (!userOptional.isPresent() && !groupOptional.isPresent()) {
            if (ScheduleParser.ParseWeek(groupNumber, "1") == null) {
                return false;
            }
            else {
                Group newGroup = new Group();
                newGroup.setGroupNumber(groupNumber);
                User newUser = new User();
                newUser.setGroup(newGroup);
                newUser.setRole(Role.USER);
                newUser.setTgId(tgId);
                groupRepository.save(newGroup);
                userRepository.save(newUser);
                return true;
            }
        }
        else {
            return false;
        }
    }

}
