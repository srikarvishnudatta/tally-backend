package com.srikar.tally.dto.expenses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExpenseRecordResponseDto {
    private int id;
    private String paidBy;
    private String owed;
    private Double amount;
}
