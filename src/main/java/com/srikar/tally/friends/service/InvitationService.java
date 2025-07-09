package com.srikar.tally.friends.service;

import com.srikar.tally.friends.dto.InvitationDto;
import com.srikar.tally.friends.dto.InvitationResponse;
import com.srikar.tally.friends.dto.InviteDto;
import com.srikar.tally.friends.model.InvitationStatus;
import com.srikar.tally.friends.model.Invitations;
import com.srikar.tally.friends.repository.InvitationRepository;
import com.srikar.tally.groups.service.GroupService;
import com.srikar.tally.users.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvitationService{
    private final InvitationRepository inviRepo;
    private final UserService userService;
    private final GroupService groupService;

    public InvitationService(InvitationRepository inviRepo, UserService userService, GroupService groupService) {
        this.inviRepo = inviRepo;
        this.userService = userService;
        this.groupService = groupService;
    }

    public Optional<Invitations> sendInvitation(String userId, InviteDto data) {
        var sender = userService.findUserById(userId);
        var receiver = userService.findUserByEmail(data.getReceiver());
        var group = groupService.findGroupById(data.getGroupId());
        if (sender.isEmpty() || receiver.isEmpty() || group.isEmpty()) return Optional.empty();
        var invitation = Invitations
                .builder()
                .sender(sender.get())
                .receiver(receiver.get())
                .status(InvitationStatus.PENDING)
                .group(group.get())
                .build();
        var invitationSaved = inviRepo.save(invitation);
        return Optional.of(invitationSaved);
    }

    public InvitationResponse getAllInvitations(String userId) {
        var user = userService.findUserById(userId).orElseThrow(() -> new RuntimeException("cant  find user"));
        return new InvitationResponse(
          user.getSentInvitations().stream().map(invite ->
               new InvitationDto(
                      invite.getId(),
                      invite.getGroup().getGroupName(),
                      invite.getReceiver().getEmail(),
                      invite.getStatus()
              )
          ).toList(),
        user.getReceivedInvitations().stream().filter(invite -> invite.getStatus().equals(InvitationStatus.PENDING)).map(invite ->
                new InvitationDto(
                        invite.getId(),
                        invite.getGroup().getGroupName(),
                        invite.getSender().getEmail(),
                        invite.getStatus()
                )).toList()
        );
    }

    @Transactional
    public void updateInvitation(String userId, int invitationId, InvitationStatus status) {
        var user = userService.findUserById(userId).orElseThrow(() -> new RuntimeException("cant find user"));
        var invitation = inviRepo.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Cannot find the invitation"));
        invitation.setStatus(status);
        inviRepo.save(invitation);
        groupService.addUserToGroup(invitation.getGroup(), user);
    }

    public void withdrawInvitation(int inviteId){
        inviRepo.deleteById(inviteId);
    }

}
