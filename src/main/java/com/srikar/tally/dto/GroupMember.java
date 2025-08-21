package com.srikar.tally.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroupMember {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
}
