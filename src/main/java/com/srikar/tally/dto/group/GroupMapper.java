package com.srikar.tally.dto.group;

import com.srikar.tally.model.Groups;
import com.srikar.tally.model.Users;


public class GroupMapper {

    public static GroupResponseDto toDto(Groups groups){
        return GroupResponseDto
                .builder()
                .id(groups.getId())
                .groupName(groups.getGroupName())
                .groupMemberList(groups.getMembers() == null ? null : groups.getMembers().stream().map(GroupMapper::toMember).toList())
                .build();
    }
    public static GroupMember toMember(Users user){
        return GroupMember
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
