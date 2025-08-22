package com.srikar.tally.controller;

import com.srikar.tally.configuration.FirebaseUserPrincipal;
import com.srikar.tally.dto.expenses.BalanceResponseDto;
import com.srikar.tally.dto.expenses.ExpenseRequestDto;
import com.srikar.tally.dto.expenses.PersonalExpenseResponseDto;
import com.srikar.tally.dto.validators.CreatePersonalExpenseValidationGroup;
import com.srikar.tally.service.PersonalExpenseService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/expenses/personal")
public class PersonalExpensesController {
    private final PersonalExpenseService personalExpenseService;

    public PersonalExpensesController(PersonalExpenseService personalExpenseService) {
        this.personalExpenseService = personalExpenseService;
    }
    @GetMapping("/balances")
    public ResponseEntity<BalanceResponseDto> getBalances(@AuthenticationPrincipal
                                                              FirebaseUserPrincipal userPrincipal){
        var balance = personalExpenseService.getBalances(userPrincipal.getUid());
        return ResponseEntity.ok(balance);
    }
    @GetMapping("/")
    public ResponseEntity<List<PersonalExpenseResponseDto>> getAllPersonalExpenses(
            @AuthenticationPrincipal FirebaseUserPrincipal userPrincipal
            ){
        var allExpense = personalExpenseService.getAllPersonalExpenses(userPrincipal.getUid());
        return ResponseEntity.ok(allExpense);
    }
    @PostMapping("/")
    public  ResponseEntity<PersonalExpenseResponseDto> createNewExpense(
            @AuthenticationPrincipal FirebaseUserPrincipal firebaseUserPrincipal,
            @Validated({Builder.Default.class, CreatePersonalExpenseValidationGroup.class})
            @RequestBody ExpenseRequestDto dto
            ){
        var expenseCreated = personalExpenseService.createPersonalExpense(
                firebaseUserPrincipal.getUid(),
                dto);
        URI location = URI.create("/api/v1/expenses/personal/" + expenseCreated.getId());
        return ResponseEntity.created(location).body(expenseCreated);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PersonalExpenseResponseDto> updateExpense(
            @PathVariable("id") int id,
            @Validated({Builder.Default.class, CreatePersonalExpenseValidationGroup.class})
            @RequestBody ExpenseRequestDto dto
    ){
        var updatedExpense = personalExpenseService.updatePersonalExpense(id, dto);
        return ResponseEntity.ok(updatedExpense);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") int id){
        personalExpenseService.deletePersonalExpense(id);
        return ResponseEntity.noContent().build();
    }
}
