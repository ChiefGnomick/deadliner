package com.resource.deadliner.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resource.deadliner.dto.FindWeekRequest;
import com.resource.deadliner.model.Week;
import com.resource.deadliner.service.WeekService;

@RestController
@RequestMapping("/api/week")
public class WeekController {

    private final WeekService weekService;

    @Autowired
    public WeekController(WeekService weekService) {
        this.weekService = weekService;
    }

    @PostMapping("/find")
    public ResponseEntity<Week> getWeek(@RequestBody FindWeekRequest request) {
        Optional<Week> result = weekService.getWeekByTgIdAndNumber(request.getTgId(), Integer.parseInt(request.getWeekNumber()));
        System.out.println(result);
        return ResponseEntity.ok(result.get());
    }
}
