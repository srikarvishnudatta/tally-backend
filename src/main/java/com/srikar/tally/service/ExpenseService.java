package com.srikar.tally.service;


import com.srikar.tally.dto.expenses.ExpenseMapper;
import com.srikar.tally.dto.expenses.ExpenseRequestDto;
import com.srikar.tally.dto.expenses.ExpenseResponseDto;
import com.srikar.tally.dto.group.GroupBalanceResponseDto;
import com.srikar.tally.dto.group.GroupMapper;
import com.srikar.tally.exception.ExpenseNotFoundException;
import com.srikar.tally.model.ExpenseRecords;
import com.srikar.tally.model.Users;
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


    public ExpenseService(ExpensesRepository expenseRepo,
                          ExpenseRecordRepository expenseRecordRepo,
                          UserService userService,
                          GroupService groupService) {
        this.expenseRepo = expenseRepo;
        this.expenseRecordRepo = expenseRecordRepo;
        this.userService = userService;
        this.groupService = groupService;

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
        var expenseCreated = ExpenseMapper.toExpense(dto);
        expenseCreated.setMyGroup(groupFound);
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

    @Transactional
    public ExpenseResponseDto updateExpense(int expenseId, ExpenseRequestDto dto){
        var groupFound = groupService.findById(dto.getGroupId());
        var paidBy = userService.getUserById(dto.getPaidBy());
        var expenseFound = expenseRepo
                .findById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense not found"));
        expenseFound.setExpenseName(dto.getExpenseName());
        expenseFound.setDescription(dto.getDescription());
        expenseFound.setAmount(dto.getAmount());
        expenseFound.setMyGroup(groupFound);
        // deleting all expense records for this expense
        expenseRecordRepo.deleteExpenseRecordsByExpense_Id(expenseId);
        List<ExpenseRecords> newRecords = dto.getSplitAmong().entrySet().stream()
                .map(entry -> ExpenseRecords.builder()
                        .owedBy(userService.getUserById(entry.getKey()))
                        .paidBy(paidBy)
                        .value(entry.getValue())
                        .expense(expenseFound)
                        .build())
                .toList();
        expenseRecordRepo.saveAll(newRecords);
        expenseFound.setExpenseRecords(newRecords);
        var expenseSaved = expenseRepo.save(expenseFound);
        return ExpenseMapper.toExpenseResponseDto(expenseSaved);
    }

    public void deleteExpense(int expenseId){
        expenseRepo.deleteById(expenseId);
    }
    public List<GroupBalanceResponseDto> calculateBalances(int groupId){
        var rawBalances = expenseRepo.calculateGroupBalances(groupId);
        return rawBalances.stream().map(row -> new GroupBalanceResponseDto(
                GroupMapper.toMember((Users) row[0]),
                GroupMapper.toMember((Users) row[1]),
                (Double) row[2]
        )).toList();
    }
}
