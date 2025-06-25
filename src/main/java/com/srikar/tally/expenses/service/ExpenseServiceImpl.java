package com.srikar.tally.expenses.service;


import com.srikar.tally.expenses.dto.ExpenseDto;
import com.srikar.tally.expenses.model.ExpenseRecords;
import com.srikar.tally.expenses.model.Expenses;
import com.srikar.tally.expenses.repository.BalanceRepository;
import com.srikar.tally.expenses.repository.ExpenseRecordRepository;
import com.srikar.tally.expenses.repository.ExpensesRepository;
import com.srikar.tally.groups.service.GroupServiceImpl;
import com.srikar.tally.users.UserRepository;
import com.srikar.tally.users.Users;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpensesRepository expenseRepo;
    private final ExpenseRecordRepository expenseRecordRepo;
    private final UserRepository userRepo;
    private final GroupServiceImpl groupService;
    private final BalanceRepository balanceRepo;

    public ExpenseServiceImpl(ExpensesRepository expenseRepo,
                              ExpenseRecordRepository expenseRecordRepo,
                              UserRepository userRepo,
                              GroupServiceImpl groupService, BalanceRepository balanceRepo) {
        this.expenseRepo = expenseRepo;
        this.expenseRecordRepo = expenseRecordRepo;
        this.userRepo = userRepo;
        this.groupService = groupService;
        this.balanceRepo = balanceRepo;
    }

    @Override
    public List<Expenses> getExpenseByGroupId(int groupId) {
        return List.of();
    }

    @Override
    @Transactional
    public void createNewExpense(int groupId, ExpenseDto data) {
        var group = groupService.findGroupById(groupId).orElseThrow();
        var newExpense = Expenses.builder()
                .expenseName(data.getExpenseName())
                .amount(data.getAmount())
                .myGroup(group)
                .description(data.getDescription())
                .build();
        var savedExpense = expenseRepo.save(newExpense);
        List<Users> splitAmong = userRepo.findAllByEmailIn(data.getSplitAmong());
        var paidBy = userRepo.findByEmail(data.getPaidBy()).orElseThrow();
        double amountSplit = data.getAmount()/splitAmong.size();
        for(var user: splitAmong){
            var expenseRecord = ExpenseRecords.builder()
                    .expenses(savedExpense)
                    .paidBy(paidBy)
                    .owedBy(user)
                    .value(amountSplit)
                    .build();
            expenseRecordRepo.save(expenseRecord);
        }
        for(var user: splitAmong){
            var balance = balanceRepo
                    .findByUser_IdAndOwesTo_IdAndBalanceGroups_Id(user.getId(), paidBy.getId(), group.getId())
                    .orElseThrow(() -> new RuntimeException("Unknown error"));
            Double amount = balance.getAmount();
            amount+=amountSplit;
            balance.setAmount(amount);
        }
    }
}
