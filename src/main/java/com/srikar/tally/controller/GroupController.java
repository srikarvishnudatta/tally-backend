package com.srikar.tally.controller;

import com.srikar.tally.configuration.FirebaseUserPrincipal;
import com.srikar.tally.dto.GroupRequestDto;
import com.srikar.tally.dto.GroupResponseDto;
import com.srikar.tally.service.GroupService;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping("/")
    public ResponseEntity<List<GroupResponseDto>> getAllGroupsByUser(
            @AuthenticationPrincipal FirebaseUserPrincipal userPrincipal
            ){
        var groups = groupService.getAllGroupsByUser(userPrincipal.getUid());
        return ResponseEntity.ok(groups);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroupByGroupId(
            @PathVariable("id") int id
    ){
        var groupFound = groupService.findGroupById(id);
        return ResponseEntity.ok(groupFound);
    }
    @PostMapping("/")
    public ResponseEntity<GroupResponseDto> createNewGroup(
            @AuthenticationPrincipal FirebaseUserPrincipal userPrincipal,
            @Validated({Builder.Default.class}) @RequestBody GroupRequestDto dto
            ){
        var groupCreated = groupService.createNewGroup(userPrincipal.getUid(),dto);
        var location = URI.create("api/v1/groups/" + groupCreated.getId());
        return ResponseEntity.created(location).body(groupCreated);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> updateGroup(
            @PathVariable("id") int id,
            @Validated({Builder.Default.class}) @RequestBody GroupRequestDto dto
    ){
        var groupUpdated = groupService.updateGroup(id, dto);
        return ResponseEntity.ok(groupUpdated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(
            @PathVariable("id") int id
    ){
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
    // this is where you add methods to remove members of a group.
}
