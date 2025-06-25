package com.srikar.tally.friends

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class InviteStatusDto @JsonCreator constructor(
    @JsonProperty("status") val status: InvitationStatus
)
// changes val status: InvitationStatus?=null give a default value to deserialize the json.