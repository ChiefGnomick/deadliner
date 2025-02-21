package com.resource.deadliner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalTaskRequest {
    
    private String author;

    private String description;

    private String Date;
    
    private int weekNumber;
}
