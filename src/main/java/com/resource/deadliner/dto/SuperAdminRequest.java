package com.resource.deadliner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuperAdminRequest {

    private String requesterTgId; 

    private String confirmationCode; 

}