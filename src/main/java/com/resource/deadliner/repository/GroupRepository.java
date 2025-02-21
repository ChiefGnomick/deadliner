package com.resource.deadliner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resource.deadliner.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupNumber(String groupNumber);
}