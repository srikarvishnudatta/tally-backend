package com.srikar.tally.expenses.repository;

import com.srikar.tally.expenses.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Integer> {
    List<Expenses> findByUser_IdAndMyGroupIsNull(String userId);
}
