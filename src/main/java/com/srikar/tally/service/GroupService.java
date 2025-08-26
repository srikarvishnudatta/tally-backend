package com.srikar.tally.service;


import com.srikar.tally.dto.group.GroupMapper;
import com.srikar.tally.dto.group.GroupRequestDto;
import com.srikar.tally.dto.group.GroupResponseDto;
import com.srikar.tally.exception.GroupNotFoundException;
import com.srikar.tally.model.Users;
import com.srikar.tally.repository.BalanceRepository;
import com.srikar.tally.model.Groups;
import com.srikar.tally.repository.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GroupService{
    private final GroupRepository groupRepo;
    private final BalanceRepository balanceRepo;
    private final UserService userService;

    public GroupService(GroupRepository groupRepo, BalanceRepository balanceRepo, UserService userService) {
        this.groupRepo = groupRepo;
        this.balanceRepo = balanceRepo;
        this.userService = userService;
    }

    public List<GroupResponseDto> getAllGroupsByUser(String userId){
        // fetch groups by owner and fetch groups by list of members.
        var groupMembers = groupRepo.findByMembersId(userId);
        return groupMembers.stream().map(GroupMapper::toDto).toList();
    }

    public GroupResponseDto findGroupById(int groupId){
        var groupFound = findById(groupId);
        return GroupMapper.toDto(groupFound);
    }
    public Groups findById(int groupId){
        return groupRepo.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group cannot be found"));
    }

    public GroupResponseDto createNewGroup(String userId, GroupRequestDto dto){
        var user = userService.getUserById(userId);
        // no need to save in user as well. jpa will handle it for us.
        var membersList = new ArrayList<Users>();
        membersList.add(user);
        var group = Groups.builder()
                .groupName(dto.getGroupName())
                .members(membersList)
                .build();
        group = groupRepo.save(group);
        return GroupMapper.toDto(group);
    }

    public GroupResponseDto updateGroup(int groupId, GroupRequestDto dto) {
        var group = findById(groupId);
        group.setGroupName(dto.getGroupName());
        group = groupRepo.save(group);
        return GroupMapper.toDto(group);
    }

    @Transactional
    public void removeMemberFromGroup(int groupId, String userId){
        var user = userService.getUserById(userId);
        var group = findById(groupId);
        group.getMembers().remove(user);
    }

    public void saveGroup(Groups groups){
        groupRepo.save(groups);
    }
    public void deleteGroup(int groupId) {
        groupRepo.deleteById(groupId);
    }
}
