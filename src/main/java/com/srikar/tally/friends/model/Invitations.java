package com.srikar.tally.friends.model;

import com.srikar.tally.groups.model.Groups;
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
public class Invitations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private InvitationStatus status;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Users receiver;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Groups group;
}
