package com.srikar.tally.expenses.repository;

import com.srikar.tally.expenses.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {
}
