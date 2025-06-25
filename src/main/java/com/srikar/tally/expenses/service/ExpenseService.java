package com.srikar.tally.expenses.service;


import com.srikar.tally.expenses.dto.ExpenseDto;
import com.srikar.tally.expenses.model.Expenses;

import java.util.List;

public interface ExpenseService {
    List<Expenses> getExpenseByGroupId(int groupId);
    void createNewExpense(int groupId, ExpenseDto dto);
}
