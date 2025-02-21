package com.resource.deadliner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeRequest {

    private String requesterTgId; 

    private String targetTgId; 

    private String newRole;

}
