package com.srikar.tally.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "owner")
    private List<Groups> ownedGroups = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private List<Groups> memberOfGroups = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Invite> sentInvitations;

    @OneToMany(mappedBy = "receiver")
    private List<Invite> receivedInvitations;

    @OneToMany(mappedBy = "paidBy")
    private List<ExpenseRecords> listPaidBy = new ArrayList<>();

    @OneToMany(mappedBy = "owedBy")
    private List<ExpenseRecords> listOwedBy = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Balances> paidBalances;

    @OneToMany(mappedBy = "owesTo")
    private List<Balances> owedBalances;

    @OneToMany(mappedBy = "user")
    private List<Expense> personalExpens;
}
