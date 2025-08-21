package com.srikar.tally.dto;

import com.srikar.tally.dto.validators.CreateExpenseValidationGroup;
import com.srikar.tally.dto.validators.CreatePersonalExpenseValidationGroup;
import com.srikar.tally.model.ExpenseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PersonalExpenseRequestDto {
    @NotBlank(message = "Expense Name cannot be empty")
    @Size(max = 50, message = "expense name cannot exceed 50 characters")
    private String expenseName;
    @NotBlank(message = "Expense Description cannot be empty")
    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;
    @NotNull(message = "Expense Amount cannot be empty")
    private Double amount;
    @NotBlank(message = "Expense Type cannot be empty", groups = {CreatePersonalExpenseValidationGroup.class})
    private ExpenseType expenseType;
    @NotBlank(message = "Paid by cannot be empty", groups = {CreateExpenseValidationGroup.class})
    @Size(max = 50, message = "Paid cannot exceed 200 characters")
    private String paidBy;
    @NotNull(message = "Split among cannot be null", groups = {CreateExpenseValidationGroup.class})
    private List<String> splitAmong;
}
