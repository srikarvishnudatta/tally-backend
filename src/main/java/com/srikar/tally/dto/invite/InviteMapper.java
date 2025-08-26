package com.srikar.tally.dto.invite;

import com.srikar.tally.model.Invite;

public class InviteMapper {
    public static InviteResponseDto toDto(Invite invite){
        return InviteResponseDto.builder()
                .id(invite.getId())
                .groupName(invite.getGroup().getGroupName())
                .sentBy(invite.getSender().getEmail())
                .status(invite.getStatus())
                .build();
    }
}
