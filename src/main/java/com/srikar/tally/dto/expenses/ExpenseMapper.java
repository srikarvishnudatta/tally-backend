package com.srikar.tally.dto.expenses;

import com.srikar.tally.model.Expense;
import com.srikar.tally.model.ExpenseRecords;

public class ExpenseMapper {
    public static PersonalExpenseResponseDto toDto(Expense expense){
        return PersonalExpenseResponseDto.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .expenseType(expense.getExpenseType())
                .createdAt(expense.getCreatedAt())
                .build();
    }
    public static ExpenseResponseDto toExpenseResponseDto(Expense expense){
        return ExpenseResponseDto.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .paidBy(expense.getUser().getFirstName())
                .shareValues(expense.getExpenseRecords().stream().map(ExpenseMapper::toRecordResponseDto).toList())
                .build();
    }
    public static ExpenseRecordResponseDto toRecordResponseDto(ExpenseRecords expenseRecord){
        return ExpenseRecordResponseDto
                .builder()
                .id(expenseRecord.getId())
                .paidBy(expenseRecord.getPaidBy().getFirstName())
                .owed(expenseRecord.getOwedBy().getFirstName())
                .amount(expenseRecord.getValue())
                .build();
    }
    public static Expense toExpense(ExpenseRequestDto dto){
        return Expense.builder()
                .expenseName(dto.getExpenseName())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .build();
    }
}
