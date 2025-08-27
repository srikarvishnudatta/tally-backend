package com.srikar.tally.dto.expenses;

import com.srikar.tally.dto.group.GroupMapper;
import com.srikar.tally.model.Expense;
import com.srikar.tally.model.ExpenseRecords;
import com.srikar.tally.model.ExpenseType;


public class ExpenseMapper {
    public static PersonalExpenseResponseDto toDto(Expense expense){
        return PersonalExpenseResponseDto.builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .expenseType(expense.getExpenseType())
                .createdAt(expense.getCreatedAt())
                .build();
    }
    public static ExpenseResponseDto toExpenseResponseDto(Expense expense, String currUserId){
        var message = expense.getExpenseRecords().stream()
                .filter(record -> !record.getPaidBy().getId().equals(record.getOwedBy().getId()))
                .map(record -> {
             if (record.getPaidBy().getId().equals(currUserId)){
                return record.getOwedBy().getFirstName() + " owes you " + record.getValue();
            }else{
                return "You owe " + record.getPaidBy().getFirstName() + " " + record.getValue();
            }
        }).findFirst().orElse("You don't owe anything");
        return ExpenseResponseDto
                .builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .paidBy(GroupMapper.toMember(expense.getUser()))
                .shareValues(expense.getExpenseRecords().stream().map(ExpenseMapper::toRecordResponseDto).toList())
                .message(message)
                .build();
    }
    public static ExpenseResponseDto toExpenseResponseDto(Expense expense){
        return ExpenseResponseDto
                .builder()
                .id(expense.getId())
                .expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .paidBy(GroupMapper.toMember(expense.getUser()))
                .shareValues(expense.getExpenseRecords().stream().map(ExpenseMapper::toRecordResponseDto).toList())
                .message("")
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
                .amount(dto.getAmount())
                .expenseType(ExpenseType.EXPENSE)
                .build();
    }
}
