package com.srikar.tally.dto;

import com.srikar.tally.model.Invite;

public class InviteMapper {
    public static InviteResponseDto toDto(Invite invite){
        return InviteResponseDto.builder()
                .groupName(invite.getGroup().getGroupName())
                .sentBy(invite.getSender().getEmail())
                .status(invite.getStatus())
                .build();
    }
}
