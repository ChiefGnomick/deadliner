package com.resource.deadliner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.deadliner.model.PersonalTask;

@Repository
public interface PersonalTaskRepository extends JpaRepository<PersonalTask, Long> {
}