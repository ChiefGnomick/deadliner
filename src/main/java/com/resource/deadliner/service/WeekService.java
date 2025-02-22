package com.resource.deadliner.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resource.deadliner.model.Group;
import com.resource.deadliner.model.User;
import com.resource.deadliner.model.Week;
import com.resource.deadliner.repository.UserRepository;
import com.resource.deadliner.repository.WeekRepository;

@Service
public class WeekService {

    private final WeekRepository weekRepository;
    private final UserRepository userRepository;

    @Autowired
    public WeekService(WeekRepository weekRepository, UserRepository userRepository) {
        this.weekRepository = weekRepository;
        this.userRepository = userRepository;
    }

    public Optional<Week> getWeekByTgIdAndNumber(String tgId, int weekNumber) {
        Optional<User> userOpt = userRepository.findByTgId(tgId);
        if (userOpt.isEmpty()) {
            return Optional.empty(); 
        }

        User user = userOpt.get();
        Group group = user.getGroup();

        Optional<Week> existingWeekOpt = weekRepository.findByGroupAndWeekNumber(group, weekNumber);
        if (existingWeekOpt.isPresent()) {
            return existingWeekOpt; 
        }

        Week parsedWeek = ScheduleParser.ParseWeek(group.getGroupNumber(), String.valueOf(weekNumber));
        if (parsedWeek != null) {
            parsedWeek.setGroup(group);
            weekRepository.save(parsedWeek);  
            return Optional.of(parsedWeek);
        }

        return Optional.empty();
    }
}