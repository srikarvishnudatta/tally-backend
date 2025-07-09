package com.srikar.tally.groups.controller


import com.srikar.tally.configuration.FirebaseUserPrincipal
import com.srikar.tally.groups.dto.GroupDto
import com.srikar.tally.groups.dto.NewGroupDto
import com.srikar.tally.groups.service.GroupService
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
import java.net.URI

@RestController
@RequestMapping("/v1/groups")
class GroupController(
    private val groupService: GroupService,
) {
    @PostMapping("/")
    fun createGroup(
        @AuthenticationPrincipal user: FirebaseUserPrincipal,
        @RequestBody data: NewGroupDto
    ): ResponseEntity<Int>{
        val userId = user.uid
        val result = groupService.createGroup(userId, data)
        val location = URI.create("/api/v1/groups/")
        return ResponseEntity.created(location).body(result.get())
    }
    @GetMapping("/{groupId}")
    fun getGroupById(
        @PathVariable("groupId") groupId: Int
    ): ResponseEntity<GroupDto>{
        val group = groupService.getGroupById(groupId)
        return ResponseEntity.ok(group.get())
    }
    @GetMapping("/")
    fun getGroupByUser(
        @AuthenticationPrincipal user: FirebaseUserPrincipal
    ): ResponseEntity<List<GroupDto>>{
        val userId = user.uid
        val results = groupService.getUserGroups(userId)
        return ResponseEntity.ok(results)
    }
    @PutMapping("/{groupId}")
    fun putGroup(@AuthenticationPrincipal user: FirebaseUserPrincipal,
                 @RequestBody data: NewGroupDto,
                 @PathVariable groupId: Int): ResponseEntity<Void>{
        groupService.updateGroup(groupId, data)
        return ResponseEntity.status(204).body(null)
    }
    @DeleteMapping("/{groupId}")
    fun deleteGroup(@PathVariable groupId: Int): ResponseEntity<Void>{
        groupService.deleteGroup(groupId)
        return ResponseEntity.noContent().build();
    }
}