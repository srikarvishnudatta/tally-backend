package com.srikar.tally.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroupBalanceResponseDto {
    private GroupMember owed;
    private GroupMember paidBy;
    private Double amount;
}
