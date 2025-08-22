package com.srikar.tally.repository;

import com.srikar.tally.model.ExpenseRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecords, Integer> {
    void deleteExpenseRecordsByExpense_Id(int expenseId);
}
