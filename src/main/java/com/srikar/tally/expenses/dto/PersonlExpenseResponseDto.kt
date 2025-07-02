package com.srikar.tally.expenses.dto

import com.srikar.tally.expenses.model.ExpenseType
import java.time.LocalDateTime

data class PersonalExpenseResponseDto(
    val id: Long,
    val expenseName: String,
    val description: String,
    val amount: Double,
    val expenseType: ExpenseType,
    val createdAt: LocalDateTime
)
