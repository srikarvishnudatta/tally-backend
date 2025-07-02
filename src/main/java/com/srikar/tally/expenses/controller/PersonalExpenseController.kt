package com.srikar.tally.expenses.controller;

import com.srikar.tally.configuration.FirebaseUserPrincipal
import com.srikar.tally.expenses.dto.PersonalExpenseDto
import com.srikar.tally.expenses.dto.PersonalExpenseResponseDto
import com.srikar.tally.expenses.model.Expenses
import com.srikar.tally.expenses.service.PersonalService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*;
import java.net.URI

@RestController
@RequestMapping("/v1/expenses/personal")
class PersonalExpenseController (
    private val personalService: PersonalService
) {
    @GetMapping("/")
    fun getPersonalExpenses(@AuthenticationPrincipal
                            firebaseUserPrincipal:
                            FirebaseUserPrincipal,
                            ): MutableList<PersonalExpenseResponseDto?> {
        return personalService.findPersonalExpenses(firebaseUserPrincipal.uid)
    }
    @PostMapping("/")
    fun postPersonalExpense(@AuthenticationPrincipal
                            firebaseUserPrincipal:
                            FirebaseUserPrincipal,
                            @RequestBody expense: PersonalExpenseDto): ResponseEntity<Expenses>{
        val created = personalService.createPersonalExpense(firebaseUserPrincipal.uid,expense)
        val location = URI.create("/api/v1/expenses/personal/")
        return ResponseEntity.created(location).body(created)
    }
    @PutMapping("/{expenseId}")
    fun putPersonalExpense(@AuthenticationPrincipal
                           firebaseUserPrincipal:
                           FirebaseUserPrincipal,
                            @PathVariable expenseId: Int,
                           @RequestBody expense: PersonalExpenseDto){
        personalService.updatePersonalExpense(expenseId, expense)
    }
    @DeleteMapping("/{expenseId}")
    fun deletePersonalExpense(@AuthenticationPrincipal
                              firebaseUserPrincipal:
                              FirebaseUserPrincipal,
                              @PathVariable expenseId: Int): ResponseEntity<Void>{
        personalService.removePersonalExpense(expenseId)
        return ResponseEntity.noContent().build()
    }
}
