package com.srikar.tally.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GroupResponseDto {
    private int id;
    private String groupName;
    private String groupDescription;
    private String owner;
    private List<GroupMember> groupMemberList;
}
