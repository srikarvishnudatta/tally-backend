package com.srikar.tally.expenses.model;

import com.srikar.tally.groups.model.Groups;
import com.srikar.tally.users.Users;
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
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups myGroup;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseType expenseType;

    @OneToMany(mappedBy = "expenses", cascade = CascadeType.ALL)
    List<ExpenseRecords> expenseRecords = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @PrePersist
    void create(){
       this.createdAt = LocalDateTime.now();
    }
}
