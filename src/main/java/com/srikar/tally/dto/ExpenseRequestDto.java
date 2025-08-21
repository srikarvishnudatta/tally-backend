package com.srikar.tally.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExpenseRequestDto {
    @NotBlank(message = "Expense Name cannot be empty")
    @Size(max = 50, message = "expense name cannot exceed 50 characters")
    private String expenseName;
    @NotBlank(message = "Expense Description cannot be empty")
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
    @NotNull(message = "Expense Amount cannot be empty")
    private Double amount;
    @NotNull(message = "Group cannot be empty")
    private int groupId;
    // user_id = paidBy
    @NotNull(message = "Paid by cannot be empty")
    private String paidBy;
    @NotNull(message = "Map of splitAmong cannot be empty")
    // {map of key-value pairs where key = owed, value = amountSplit, it is explicit this map owes to paidBy
    private Map<@NotNull String, Double> splitAmong;
}
