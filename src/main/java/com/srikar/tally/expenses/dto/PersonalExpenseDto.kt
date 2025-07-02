package com.srikar.tally.expenses.dto


data class PersonalExpenseDto (
    val expenseName: String,
    val description:String,
    val amount: Double,
    val expenseType: String
)