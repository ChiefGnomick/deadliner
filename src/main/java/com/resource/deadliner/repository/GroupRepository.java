package com.resource.deadliner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.deadliner.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupNumber(String groupNumber);
}