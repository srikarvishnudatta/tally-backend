package com.srikar.tally.controller;


import com.srikar.tally.dto.expenses.ExpenseRequestDto;
import com.srikar.tally.dto.expenses.ExpenseResponseDto;
import com.srikar.tally.dto.group.GroupBalanceResponseDto;
import com.srikar.tally.dto.validators.CreateExpenseValidationGroup;
import com.srikar.tally.service.ExpenseService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<List<ExpenseResponseDto>> getExpenseByGroupId(
            @PathVariable("groupId") int groupId
    ){
        var expenses = expenseService.getExpenseListByGroup(groupId);
        return ResponseEntity.ok(expenses);
    }
//    @GetMapping("/{expenseId}")
//    public ResponseEntity<ExpenseResponseDto> getExpenseById(
//            @PathVariable("expenseId") int expenseId
//    ){
//        var expense = expenseService.getExpenseById(expenseId);
//        return ResponseEntity.ok(expense);
//    }
    @PostMapping("/")
    public ResponseEntity<ExpenseResponseDto> createNewExpense(
            @Validated({Builder.Default.class, CreateExpenseValidationGroup.class}) @RequestBody ExpenseRequestDto dto
            ){
        var expenseCreated = expenseService.createExpense(dto);
        URI location = URI.create("/api/v1/expense/" + expenseCreated.getId());
        return ResponseEntity.created(location).body(expenseCreated);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDto> updateExpense(
            @PathVariable("id") int id,
            @Validated({Builder.Default.class, CreateExpenseValidationGroup.class}) @RequestBody ExpenseRequestDto dto
    ){
       var expenseUpdated = expenseService.updateExpense(id, dto);
       return ResponseEntity.ok(expenseUpdated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable("id") int expenseId
    ){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{groupId}/balances")
    public ResponseEntity<List<GroupBalanceResponseDto>> getGroupBalances(
            @PathVariable("groupId") int groupId
    ){
        var balances = expenseService.calculateBalances(groupId);
        return ResponseEntity.ok(balances);
    }
}
