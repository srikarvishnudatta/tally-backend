package com.srikar.tally.service;

import com.srikar.tally.dto.expenses.BalanceResponseDto;
import com.srikar.tally.dto.expenses.PersonalExpenseRequestDto;
import com.srikar.tally.dto.expenses.ExpenseMapper;
import com.srikar.tally.exception.ExpenseNotFoundException;
import com.srikar.tally.dto.expenses.PersonalExpenseResponseDto;
import com.srikar.tally.model.ExpenseType;
import com.srikar.tally.model.Expense;
import com.srikar.tally.repository.ExpensesRepository;
import com.srikar.tally.model.Users;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalExpenseService {
    private final ExpensesRepository expensesRepository;
    private final EntityManager entityManager;
    public PersonalExpenseService(ExpensesRepository expensesRepository, EntityManager entityManager) {
        this.expensesRepository = expensesRepository;
        this.entityManager = entityManager;
    }
    public BalanceResponseDto getBalances(String userId){
        var income = expensesRepository.getAmount(userId, ExpenseType.INCOME);
        var expenditure = expensesRepository.getAmount(userId, ExpenseType.EXPENSE);
        return BalanceResponseDto.builder().income(income).expenditure(expenditure).build();
    }

    public List<PersonalExpenseResponseDto> getAllPersonalExpenses(String userId){
        return expensesRepository
                .findByUser_IdAndMyGroupIsNull(userId)
                .stream()
                .map(ExpenseMapper::toDto)
                .toList();
    }

    public PersonalExpenseResponseDto createPersonalExpense(String userId, PersonalExpenseRequestDto expense) {
        var user = entityManager.getReference(Users.class, userId);
        var newExpense = Expense
                .builder()
                .expenseName(expense.getExpenseName())
                .amount(expense.getAmount())
                .user(user)
                .expenseType(expense.getExpenseType())
                .description(expense.getDescription()).build();
        newExpense = expensesRepository.save(newExpense);
        return ExpenseMapper.toDto(newExpense);
    }
    public PersonalExpenseResponseDto updatePersonalExpense(int expenseId, PersonalExpenseRequestDto dto){
        var expense = expensesRepository.findById(expenseId).orElseThrow(() -> new ExpenseNotFoundException("Expense Cannot be found"));
        expense.setExpenseName(dto.getExpenseName());
        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setExpenseType(dto.getExpenseType());
        expense = expensesRepository.save(expense);
        return ExpenseMapper.toDto(expense);
    }
    public void deletePersonalExpense(int expenseId){
        expensesRepository.deleteById(expenseId);
    }
}