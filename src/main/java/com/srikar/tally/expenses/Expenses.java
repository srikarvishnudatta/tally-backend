package com.srikar.tally.expenses;

import com.srikar.tally.groups.Groups;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String expenseName;

    private String description;

    private Double amount;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups myGroup;

    @OneToMany(mappedBy = "expenses", cascade = CascadeType.ALL)
    List<ExpenseRecords> expenseRecords = new ArrayList<>();

}
