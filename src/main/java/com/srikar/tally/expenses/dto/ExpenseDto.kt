package com.srikar.tally.expenses.dto

data class ExpenseDto(
    val expenseName: String,
    val description:String,
    val amount: Double,
    val paidBy: String,
    val splitAmong: List<String>
)
