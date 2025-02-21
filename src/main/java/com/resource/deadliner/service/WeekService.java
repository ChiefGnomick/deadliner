package com.resource.deadliner.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userRepository.findByTgId(tgId)
                .flatMap(user -> weekRepository.findByWeekNumber(weekNumber)
                        .filter(week -> week.getGroup().equals(user.getGroup())));
    }
}