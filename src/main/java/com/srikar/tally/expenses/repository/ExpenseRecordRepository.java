package com.srikar.tally.expenses.repository;

import com.srikar.tally.expenses.model.ExpenseRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecords, Integer> {
}
