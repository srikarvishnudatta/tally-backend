package com.srikar.tally.friends.dto

import com.srikar.tally.friends.model.InvitationStatus

data class InvitationDto(
    val id: Int,
    val groupName:String,
    val email:String,
    val status: InvitationStatus
)
data class InvitationResponse(
    val sentInvitations: List<InvitationDto>,
    val receivedInvitations: List<InvitationDto>
)
