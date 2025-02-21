package com.resource.deadliner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "groups")
public class Group {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_number", unique = true, nullable = false)
    private String groupNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Week> weeks = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
        user.setGroup(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.setGroup(null);
    }

    public void addWeek(Week week) {
        weeks.add(week);
        week.setGroup(this);
    }

    public void removeWeek(Week week) {
        weeks.remove(week);
        week.setGroup(null);
    }
}
