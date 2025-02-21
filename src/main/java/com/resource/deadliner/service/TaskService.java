package com.resource.deadliner.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.deadliner.dto.PersonalTaskRequest;
import com.resource.deadliner.dto.TaskRequest;
import com.resource.deadliner.model.Day;
import com.resource.deadliner.model.Group;
import com.resource.deadliner.model.PersonalTask;
import com.resource.deadliner.model.Task;
import com.resource.deadliner.model.User;
import com.resource.deadliner.model.Week;
import com.resource.deadliner.repository.GroupRepository;
import com.resource.deadliner.repository.PersonalTaskRepository;
import com.resource.deadliner.repository.TaskRepository;
import com.resource.deadliner.repository.UserRepository;
import com.resource.deadliner.repository.WeekRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final PersonalTaskRepository personalTaskRepository;
    private final WeekRepository weekRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    
    @Autowired
    public TaskService(TaskRepository taskRepository, PersonalTaskRepository personalTaskRepository, 
                       WeekRepository weekRepository, GroupRepository groupRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.personalTaskRepository = personalTaskRepository;
        this.weekRepository = weekRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskRequest request) throws NumberFormatException {
        Task task = new Task();
        User currentUser = userRepository.findByTgId(request.getAuthor()).get();
        String groupName = currentUser.getGroup().getGroupNumber();
        task.setUser(currentUser);
        task.setDate(LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yy.MM.dd")));
        task.setDescription(request.getDescription());
        task.setGroup(currentUser.getGroup());
        task.setSubject(request.getSubject());
        Optional<Week> existingWeek = weekRepository.findByWeekNumberAndGroup_GroupNumber(request.getWeekNumber(), groupName);
        Week week;

        if (existingWeek.isEmpty()) {
            week = ScheduleParser.ParseWeek(request.getAuthor(), String.valueOf(request.getWeekNumber()));
            if (week != null) {
                Group group = groupRepository.findByGroupNumber(groupName).get();
                week.setGroup(group);
                weekRepository.save(week);
            } else {
                throw new RuntimeException("Failed to parse week: " + request.getWeekNumber());
            }
        } else {
            week = existingWeek.get();
        }

        Optional<Day> dayOptional = week.getDays().stream()
            .filter(d -> d.getDate().equals(task.getDate()))
            .findFirst();

        if (dayOptional.isEmpty()) {
            throw new RuntimeException("No schedule found for this date: " + task.getDate());
        }

        Day day = dayOptional.get();
        task.setDay(day); 

        return taskRepository.save(task);
    }

    public PersonalTask createPersonalTask(PersonalTaskRequest request) {
        User currentUser = userRepository.findByTgId(request.getAuthor()).get();
        PersonalTask personalTask = new PersonalTask();
        personalTask.setDescription(request.getDescription());
        personalTask.setAuthor(currentUser);
        personalTask.setDate(LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("yy.MM.dd")));
        return personalTaskRepository.save(personalTask);
    }
}