package com.resource.deadliner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.deadliner.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByUser_TgId(String tgId); 
    List<Task> findByGroup_Id(Long groupId); 
}