package com.srikar.tally.expenses;

import com.srikar.tally.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expenses expenses;

    @ManyToOne
    @JoinColumn(name = "paid_by")
    private Users paidBy;

    @ManyToOne
    @JoinColumn(name = "owed_by")
    private Users owedBy;

    private Double value;

    // further add split value or share value.
}
