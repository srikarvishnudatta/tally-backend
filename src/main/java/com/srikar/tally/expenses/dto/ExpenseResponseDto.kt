package com.srikar.tally.expenses.dto

data class ExpenseResponseDto(
    val id: Int,
    val expenseName: String,
    val description:String,
    val amount: Double,
    val paidBy: String,
)
