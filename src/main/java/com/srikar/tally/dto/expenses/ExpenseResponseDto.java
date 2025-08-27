package com.srikar.tally.dto.expenses;

import com.srikar.tally.dto.group.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ExpenseResponseDto {
    private int id;
    private String expenseName;
    private Double amount;
    private GroupMember paidBy;
    private List<ExpenseRecordResponseDto> shareValues;
    private String message;
}