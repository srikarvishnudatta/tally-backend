package com.srikar.tally.friends.controller


import com.srikar.tally.configuration.FirebaseUserPrincipal
import com.srikar.tally.friends.dto.InvitationResponse
import com.srikar.tally.friends.dto.InviteDto
import com.srikar.tally.friends.dto.InviteStatusDto
import com.srikar.tally.friends.model.Invitations
import com.srikar.tally.friends.service.InvitationService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/v1/invites")
class InviteController(
    private val inviteService: InvitationService
) {
    @GetMapping("/")
    fun getAllInvites(@AuthenticationPrincipal user: FirebaseUserPrincipal): ResponseEntity<InvitationResponse>{
        val invitations = inviteService.getAllInvitations(user.uid)
        return ResponseEntity.ok(invitations)
    }
    @PutMapping("/{inviteId}")
    fun updateInvite(@AuthenticationPrincipal user: FirebaseUserPrincipal,
        @PathVariable("inviteId") inviteId: Int,
                    @RequestBody status: InviteStatusDto
                     ): ResponseEntity<Nothing?> {
        inviteService.updateInvitation(user.uid, inviteId, status.status)
        return ResponseEntity.status(204).body(null)
    }
    @PostMapping("/request")
    fun sendInvite(
        @AuthenticationPrincipal user:FirebaseUserPrincipal,
        @RequestBody data: InviteDto
    ): ResponseEntity<Optional<Invitations>>{
        val result = inviteService.sendInvitation(user.uid, data)
        if (result.isEmpty) return ResponseEntity.status(400).body(result)
        return ResponseEntity.ok(result)
    }
    @DeleteMapping("/{inviteId}/withdraw")
    fun deleteInvite(
        @PathVariable inviteId: Int
    ): ResponseEntity<Void>{
        inviteService.withdrawInvitation(inviteId)
        return ResponseEntity.noContent().build()
    }
}