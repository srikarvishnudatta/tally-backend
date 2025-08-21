package com.srikar.tally.service;


import com.srikar.tally.dto.GroupMapper;
import com.srikar.tally.dto.GroupRequestDto;
import com.srikar.tally.dto.GroupResponseDto;
import com.srikar.tally.exception.GroupNotFoundException;
import com.srikar.tally.repository.BalanceRepository;
import com.srikar.tally.model.Groups;
import com.srikar.tally.repository.GroupRepository;
import com.srikar.tally.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class GroupService{
    private final GroupRepository groupRepo;
    private final UserRepository userRpo;
    private final BalanceRepository balanceRepo;

    public GroupService(GroupRepository groupRepo, UserRepository userRpo, BalanceRepository balanceRepo) {
        this.groupRepo = groupRepo;
        this.userRpo = userRpo;
        this.balanceRepo = balanceRepo;
    }

    public List<GroupResponseDto> getAllGroupsByUser(String userId){
        // fetch groups by owner and fetch groups by list of members.
        var groupsOwner = groupRepo.findGroupsByOwner_Id(userId);
        var groupMembers = groupRepo.findByMembersId(userId);
        groupsOwner.addAll(groupMembers);
        return groupsOwner.stream().map(GroupMapper::toDto).toList();
    }

    public GroupResponseDto findGroupById(int groupId){
        var groupFound = groupRepo.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException("Group cannot be found"));
        return GroupMapper.toDto(groupFound);
    }
    public Groups findById(int groupId){
        return groupRepo.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group cannot be found"));
    }

    public GroupResponseDto createNewGroup(String userId, GroupRequestDto dto){
        var user = userRpo.findById(userId)
                .orElseThrow(() -> new GroupNotFoundException("User and group cannot be found"));
        // no need to save in user as well. jpa will handle it for us.
        var group = Groups.builder()
                .groupName(dto.getGroupName())
                .groupDescription(dto.getGroupDescription())
                .owner(user)
                .build();
        group = groupRepo.save(group);
        return GroupMapper.toDto(group);
    }


    public GroupResponseDto updateGroup(int groupId, GroupRequestDto dto) {
        var group = groupRepo.findById(groupId).orElseThrow(() -> new GroupNotFoundException("Group Cannot be found"));
        group.setGroupName(dto.getGroupName());
        group.setGroupDescription(dto.getGroupDescription());
        group = groupRepo.save(group);
        return GroupMapper.toDto(group);
    }

    public void deleteGroup(int groupId) {
        groupRepo.deleteById(groupId);
    }
}
