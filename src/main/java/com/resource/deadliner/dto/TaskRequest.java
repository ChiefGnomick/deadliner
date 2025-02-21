package com.resource.deadliner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {
    
    private String author;

    private String subject;

    private String description;

    private String Date;
    
    private int weekNumber;
}
