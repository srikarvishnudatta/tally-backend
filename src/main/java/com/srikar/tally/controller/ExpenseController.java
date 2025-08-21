package com.srikar.tally.controller;


import com.srikar.tally.dto.ExpenseRequestDto;
import com.srikar.tally.dto.ExpenseResponseDto;
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
    @GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(
            @PathVariable("expenseId") int expenseId
    ){
        var expense = expenseService.getExpenseById(expenseId);
        return ResponseEntity.ok(expense);
    }
    @PostMapping("/")
    public ResponseEntity<ExpenseResponseDto> createNewExpense(
            @Validated({Builder.Default.class}) @RequestBody ExpenseRequestDto dto
            ){
        var expenseCreated = expenseService.createExpense(dto);
        URI location = URI.create("/api/v1/expense/" + expenseCreated.getId());
        return ResponseEntity.created(location).body(expenseCreated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(
            @PathVariable("id") int expenseId
    ){
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.noContent().build();
    }
}
