package com.srikar.tally.expenses.controller;

import com.srikar.tally.expenses.dto.ExpenseDto
import com.srikar.tally.expenses.dto.ExpenseResponseDto
import com.srikar.tally.expenses.service.ExpenseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expenses")
class ExpenseController (
    private val expenseService: ExpenseService
) {
    @GetMapping("/{groupId}")
    fun getAllExpensesByGroup(@PathVariable("groupId") groupId: Int)
    :ResponseEntity<List<ExpenseResponseDto>>{
        return ResponseEntity.ok(expenseService.getExpenseByGroupId(groupId))
    }
    @PostMapping("/{groupId}")
    @ResponseStatus(HttpStatus.CREATED)
    fun newExpense(@PathVariable("groupId") groupId: Int,
                   @RequestBody dto: ExpenseDto
    ){
        expenseService.createNewExpense(groupId, dto)
    }
}
