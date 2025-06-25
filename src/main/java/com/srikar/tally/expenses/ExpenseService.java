package com.srikar.tally.expenses;


import java.util.List;

public interface ExpenseService {
    List<Expenses> getExpenseByGroupId(int groupId);
    void createNewExpense(int groupId, ExpenseDto dto);
}
