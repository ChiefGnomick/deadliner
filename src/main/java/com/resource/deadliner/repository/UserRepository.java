package com.resource.deadliner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.resource.deadliner.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTgId(String tgId);
}