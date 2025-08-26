package com.srikar.tally.dto.invite;

import com.srikar.tally.model.InviteStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InviteResponseDto {
    private int id;
    private String groupName;
    private String sentBy;
    private InviteStatus status;
}
