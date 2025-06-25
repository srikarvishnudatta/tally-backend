package com.srikar.tally.friends.service;

import com.srikar.tally.friends.dto.InvitationResponse;
import com.srikar.tally.friends.model.InvitationStatus;
import com.srikar.tally.friends.model.Invitations;
import com.srikar.tally.friends.dto.InviteDto;

import java.util.Optional;

public interface InvitationService {
    Optional<Invitations> sendInvitation(String userId, InviteDto data);
    InvitationResponse getAllInvitations(String userId);
    void updateInvitation(String userId, int invitationId, InvitationStatus status);
    int getInvitationCount(String userId);
}
