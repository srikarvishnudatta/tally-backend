package com.srikar.tally.groups

data class GroupDto(
    val id: Int,
    val groupName:String,
    val groupDescription:String,
    val isAdmin: Boolean?,
    val members: List<Member>,
)
data class Member(
    val email:String,
    val firstName: String,
    val lastName: String
)
