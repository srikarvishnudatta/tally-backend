package com.srikar.tally.repository;

import com.srikar.tally.model.ExpenseType;
import com.srikar.tally.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ExpensesRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByUser_IdAndMyGroupIsNull(String userId);
    List<Expense> findExpensesByMyGroup_Id(int groupId);

    @Query("SELECT SUM(e.amount) FROM Expense e " +
            "WHERE e.user.id=:userId AND e.myGroup is NULL AND e.expenseType=:type")
    double getAmount(String userId, ExpenseType type);

    @Query("SELECT er.owedBy, er.paidBy, SUM(er.value) FROM ExpenseRecords er where er.expense.myGroup.id=:groupId GROUP BY er.owedBy.id, er.paidBy.id")
    List<Object[]> calculateGroupBalances(@Param("groupId") int groupId);

}
