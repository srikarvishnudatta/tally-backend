package com.srikar.tally.dto.expenses;

import com.srikar.tally.dto.validators.CreateExpenseValidationGroup;
import com.srikar.tally.dto.validators.CreatePersonalExpenseValidationGroup;
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
    @NotNull(message = "Group cannot be empty", groups = {CreateExpenseValidationGroup.class})
    private Integer groupId;
    @NotBlank(message = "Expense Type cannot be empty", groups = {CreatePersonalExpenseValidationGroup.class})
    private String expenseType;
    // uuid of paid_by
    @NotBlank(message = "Paid by cannot be empty", groups = {CreateExpenseValidationGroup.class})
    @Size(max = 100, message = "Paid cannot exceed 100 characters")
    private String paidBy;
    @NotNull(message = "Split among cannot be null", groups = {CreateExpenseValidationGroup.class})
    private Map<String, Double> splitAmong;
}
