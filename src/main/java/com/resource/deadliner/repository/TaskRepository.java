package com.resource.deadliner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.deadliner.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}