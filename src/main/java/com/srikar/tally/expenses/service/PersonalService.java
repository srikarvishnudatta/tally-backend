package com.srikar.tally.expenses.service;

import com.srikar.tally.expenses.dto.PersonalExpenseDto;
import com.srikar.tally.expenses.dto.PersonalExpenseResponseDto;
import com.srikar.tally.expenses.model.ExpenseType;
import com.srikar.tally.expenses.model.Expenses;
import com.srikar.tally.expenses.repository.ExpensesRepository;
import com.srikar.tally.users.Users;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalService {
    private final ExpensesRepository expensesRepository;
    private final EntityManager entityManager;
    public PersonalService(ExpensesRepository expensesRepository, EntityManager entityManager) {
        this.expensesRepository = expensesRepository;
        this.entityManager = entityManager;
    }

    public List<PersonalExpenseResponseDto> findPersonalExpenses(String userId) {
        var result = expensesRepository.findByUser_IdAndMyGroupIsNull(userId);
        return result.stream().map(expenses -> new PersonalExpenseResponseDto(expenses.getId(),
                expenses.getExpenseName(),
                expenses.getDescription(),
                expenses.getAmount(),
                expenses.getExpenseType(),
                expenses.getCreatedAt())).toList();
    }

    public Expenses createPersonalExpense(String userId, PersonalExpenseDto expense) {
        var user = entityManager.getReference(Users.class, userId);
        var newExpense = Expenses.builder().expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .user(user)
                .expenseType(expense.getExpenseType().equals("expense") ? ExpenseType.EXPENSE : ExpenseType.INCOME)
                .description(expense.getDescription()).build();
        return expensesRepository.save(newExpense);
    }
    public void updatePersonalExpense(int expenseId, PersonalExpenseDto dto){
        expensesRepository.findById(expenseId).map(
                expense -> {
                    expense.setExpenseName(dto.getExpenseName());
                    expense.setDescription(dto.getDescription());
                    expense.setAmount(dto.getAmount());
                    expense.setExpenseType(dto.getExpenseType().equals("expense") ? ExpenseType.EXPENSE : ExpenseType.INCOME);
                    return expensesRepository.save(expense);
                }
        );
    }
    public void removePersonalExpense(int expenseId){
        expensesRepository.deleteById(expenseId);
    }
}
