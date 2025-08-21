package com.srikar.tally.service;


import com.srikar.tally.dto.ExpenseMapper;
import com.srikar.tally.dto.ExpenseRequestDto;
import com.srikar.tally.dto.ExpenseResponseDto;
import com.srikar.tally.exception.ExpenseNotFoundException;
import com.srikar.tally.model.Expense;
import com.srikar.tally.model.ExpenseRecords;
import com.srikar.tally.repository.BalanceRepository;
import com.srikar.tally.repository.ExpenseRecordRepository;
import com.srikar.tally.repository.ExpensesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService  {
    private final ExpensesRepository expenseRepo;
    private final ExpenseRecordRepository expenseRecordRepo;
    private final UserService userService;
    private final GroupService groupService;
    private final BalanceRepository balanceRepo;

    public ExpenseService(ExpensesRepository expenseRepo,
                          ExpenseRecordRepository expenseRecordRepo,
                          UserService userService,
                          GroupService groupService, BalanceRepository balanceRepo) {
        this.expenseRepo = expenseRepo;
        this.expenseRecordRepo = expenseRecordRepo;
        this.userService = userService;
        this.groupService = groupService;
        this.balanceRepo = balanceRepo;
    }
    public List<ExpenseResponseDto> getExpenseListByGroup(int groupId){
        return expenseRepo.findExpensesByMyGroup_Id(groupId).stream().map(ExpenseMapper::toExpenseResponseDto).toList();
    }
    public ExpenseResponseDto getExpenseById(int expenseId){
        var expenseFound = expenseRepo.findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense cannot be found"));
        return ExpenseMapper.toExpenseResponseDto(expenseFound);
    }
    @Transactional
    public ExpenseResponseDto createExpense(ExpenseRequestDto dto){
        var groupFound = groupService.findById(dto.getGroupId());
        var paidBy = userService.getUserById(dto.getPaidBy());
        var expenseCreated = Expense.builder()
                .expenseName(dto.getExpenseName())
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .myGroup(groupFound)
                .build();
        dto.getSplitAmong().forEach((key, value) -> {
            ExpenseRecords record = ExpenseRecords.builder()
                    .owedBy(userService.getUserById(key))
                    .paidBy(paidBy)
                    .value(value)
                    .build();
            expenseRecordRepo.save(record);
            expenseCreated.addExpenseRecord(record);
        });
        var expense = expenseRepo.save(expenseCreated);
        return ExpenseMapper.toExpenseResponseDto(expense);
    }
    public void deleteExpense(int expenseId){
        expenseRepo.deleteById(expenseId);
    }
}
