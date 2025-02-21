package com.resource.deadliner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.deadliner.model.Week;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    Optional<Week> findByWeekNumber(int weekNumber);
}
