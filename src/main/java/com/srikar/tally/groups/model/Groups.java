package com.srikar.tally.groups.model;

import com.srikar.tally.expenses.model.Balances;
import com.srikar.tally.expenses.model.Expenses;
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
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String groupName;
    private String groupDescription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroups> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "balanceGroups")
    private List<Balances> balances = new ArrayList<>();

    @OneToMany(mappedBy = "myGroup")
    private List<Expenses> expenses = new ArrayList<>();

    @PrePersist
    void prePersist(){
        createdAt = LocalDateTime.now();
        modifiedAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate(){
        modifiedAt = LocalDateTime.now();
    }
}
