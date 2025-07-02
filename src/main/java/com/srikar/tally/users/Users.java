package com.srikar.tally.users;

import com.srikar.tally.expenses.model.Balances;
import com.srikar.tally.expenses.model.ExpenseRecords;
import com.srikar.tally.expenses.model.Expenses;
import com.srikar.tally.friends.model.Invitations;
import com.srikar.tally.groups.model.UserGroups;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGroups> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Invitations> sentInvitations;

    @OneToMany(mappedBy = "receiver")
    private List<Invitations> receivedInvitations;

    @OneToMany(mappedBy = "paidBy")
    private List<ExpenseRecords> listPaidBy = new ArrayList<>();

    @OneToMany(mappedBy = "owedBy")
    private List<ExpenseRecords> listOwedBy = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Balances> paidBalances;

    @OneToMany(mappedBy = "owesTo")
    private List<Balances> owedBalances;

    @OneToMany(mappedBy = "user")
    private List<Expenses> personalExpenses;
}
