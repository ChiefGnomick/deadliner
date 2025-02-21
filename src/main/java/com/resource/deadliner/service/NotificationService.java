package com.resource.deadliner.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.resource.deadliner.dto.TaskResponse;
import com.resource.deadliner.model.Task;
import com.resource.deadliner.model.User;
import com.resource.deadliner.repository.TaskRepository;
import com.resource.deadliner.repository.UserRepository;

@Service
public class NotificationService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public NotificationService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponse getUserTasks(String tgId) {
        User user = userRepository.findByTgId(tgId).orElse(null);
        if (user == null) {
            return null; 
        }

        List<Task> personalTasks = taskRepository.findByUser_TgId(tgId);

        List<Task> groupTasks = taskRepository.findByGroup_Id(user.getGroup().getId());

        TaskResponse response = new TaskResponse();
        
        response.setPersonalTasks(personalTasks.stream()
                .map(Task::getDescription)
                .collect(Collectors.toList()));

        response.setGroupTasks(groupTasks.stream()
                .map(Task::getDescription)
                .collect(Collectors.toList())); 

        return response;
    }
}