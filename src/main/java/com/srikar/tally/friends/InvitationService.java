package com.srikar.tally.friends;

import java.util.Optional;

public interface InvitationService {
    Optional<Invitations> sendInvitation(String userId, InviteDto data);
    InvitationResponse getAllInvitations(String userId);
    void updateInvitation(String userId, int invitationId, InvitationStatus status);
    int getInvitationCount(String userId);
}
