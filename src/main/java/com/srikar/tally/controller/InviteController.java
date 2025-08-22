package com.srikar.tally.controller;

import com.srikar.tally.configuration.FirebaseUserPrincipal;
import com.srikar.tally.dto.invite.InviteResponseDto;
import com.srikar.tally.service.InviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/invites")
public class InviteController {
    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }
    @GetMapping("/")
    public ResponseEntity<List<InviteResponseDto>> getAllInvites(
            @AuthenticationPrincipal FirebaseUserPrincipal userPrincipal
            ){
        var invites = inviteService.getInvitations(userPrincipal.getUid());
        return ResponseEntity.ok(invites);
    }
    @GetMapping("/sent")
    public ResponseEntity<List<InviteResponseDto>> getSentInvites(
            @AuthenticationPrincipal FirebaseUserPrincipal userPrincipal
    ){
        var invitesSent = inviteService.getSentInvitations(userPrincipal.getUid());
        return ResponseEntity.ok(invitesSent);
    }
    // this is cancel
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvite(
            @PathVariable("id") int id
    ){
        inviteService.deleteInvite(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/accept")
    public ResponseEntity<?> acceptInvite(
            @AuthenticationPrincipal FirebaseUserPrincipal firebaseUserPrincipal,
            @PathVariable("id") int id
    ){
        inviteService.acceptInvite(firebaseUserPrincipal.getUid(), id);
        return ResponseEntity.ok("Invite Accepted");
    }
    @GetMapping("/{id}/decline")
    public ResponseEntity<?> declineInvite(
            @AuthenticationPrincipal FirebaseUserPrincipal firebaseUserPrincipal,
            @PathVariable("id") int id
    ){
        inviteService.declineInvite(firebaseUserPrincipal.getUid(), id);
        return ResponseEntity.ok("Invite Declined");
    }

}
