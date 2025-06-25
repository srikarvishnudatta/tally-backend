package com.srikar.tally.groups.service;


import com.srikar.tally.expenses.repository.BalanceRepository;
import com.srikar.tally.expenses.model.Balances;
import com.srikar.tally.groups.dto.GroupDto;
import com.srikar.tally.groups.dto.Member;
import com.srikar.tally.groups.dto.NewGroupDto;
import com.srikar.tally.groups.model.Groups;
import com.srikar.tally.groups.model.UserGroups;
import com.srikar.tally.groups.repository.GroupRepository;
import com.srikar.tally.groups.repository.UserGroupRepository;
import com.srikar.tally.users.UserRepository;
import com.srikar.tally.users.Users;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepo;
    private final UserRepository userRpo;
    private final UserGroupRepository ugRepo;
    private final BalanceRepository balanceRepo;

    public GroupServiceImpl(GroupRepository groupRepo, UserRepository userRpo, UserGroupRepository ugRepo, BalanceRepository balanceRepo) {
        this.groupRepo = groupRepo;
        this.userRpo = userRpo;
        this.ugRepo = ugRepo;
        this.balanceRepo = balanceRepo;
    }

    @Override
    @Transactional
    public List<GroupDto> getUserGroups(String userId) {
        var result = ugRepo.findGroupsByUserId(userId);
        return result.stream().map(ug ->{
            var group = ug.getGroup();
            var members = group.getUserGroups().stream()
                    .map(gg -> {
                        var user = gg.getUser();
                        return new Member(user.getEmail(), user.getFirstName(), user.getLastName());
                    }).toList();
            return new GroupDto(
                    group.getId(),
                    group.getGroupName(),
                    group.getGroupDescription(),
                    ug.isAdmin(),
                    members
            );
        }).toList();
    }

    @Override
    public Optional<Groups> findGroupById(int groupId){
        return groupRepo.findById(groupId);
    }

    @Override
    public Optional<GroupDto> getGroupById(int groupId) {
        var result = groupRepo.findById(groupId);
        if (result.isEmpty()) return Optional.empty();
        var group = result.get();
        // Build the list of members
        var members = group.getUserGroups().stream().map(ug -> {
            var user = ug.getUser();
            return new Member(user.getEmail(), user.getFirstName(), user.getLastName());
        }).toList();
        var dto = new GroupDto(
                group.getId(),
                group.getGroupName(),
                group.getGroupDescription(),
                null,
                members
        );
        return Optional.of(dto);
    }

    @Override
    @Transactional
    public Optional<Integer> createGroup(String userId, NewGroupDto data) {
        var user = userRpo.findById(userId);
        if (user.isEmpty()) return Optional.empty();
        var group = Groups.builder()
                .groupName(data.getGroupName())
                .groupDescription(data.getGroupDescription())
                .build();
        var groupCreated = groupRepo.save(group);
        var link = UserGroups
                .builder()
                .group(groupCreated)
                .user(user.get())
                .isAdmin(true)
                .build();
        ugRepo.save(link);
        return Optional.of(groupCreated.getId());
    }

    @Override
    public void addUserToGroup(Groups group, Users user) {
        var ug = UserGroups.builder().group(group).user(user).isAdmin(false).build();
        var savedGroup = ugRepo.save(ug).getGroup();
        var otherMembers = savedGroup
                .getUserGroups().stream()
                .map(UserGroups::getUser)
                .filter(u -> !u.getId().equals(user.getId()))
                .toList();
        var balances = new ArrayList<Balances>();
        for(Users member: otherMembers){
            var b1 = Balances.builder()
                    .amount(0.0)
                    .user(user)
                    .owesTo(member)
                    .balanceGroups(savedGroup)
                    .build();
            var b2 = Balances.builder()
                    .amount(0.0)
                    .user(member)
                    .owesTo(user)
                    .balanceGroups(savedGroup)
                    .build();
            balances.add(b1);
            balances.add(b2);
        }
        balanceRepo.saveAll(balances);
    }

    @Override
    public Optional<Boolean> updateGroup(int groupId, NewGroupDto dto) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> deleteGroup(int groupId) {
        return Optional.empty();
    }
}
