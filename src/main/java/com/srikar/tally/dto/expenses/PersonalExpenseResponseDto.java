package com.srikar.tally.dto.expenses;

import com.srikar.tally.model.ExpenseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
public class PersonalExpenseResponseDto{
    private int id;
    private String expenseName;
    private String description;
    private Double amount;
    private ExpenseType expenseType;
    private LocalDateTime createdAt;
}