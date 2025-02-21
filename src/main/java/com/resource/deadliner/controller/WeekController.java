package com.resource.deadliner.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> getWeek(@RequestBody Map<String, String> request) {
        String tgId = request.get("tg_id");
        int weekNumber = Integer.parseInt(request.get("week_number"));
        Optional<Week> week = weekService.getWeekByTgIdAndNumber(tgId, weekNumber);
        return week.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
