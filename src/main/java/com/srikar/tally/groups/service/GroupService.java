package com.srikar.tally.groups.service;



import com.srikar.tally.groups.dto.GroupDto;
import com.srikar.tally.groups.model.Groups;
import com.srikar.tally.groups.dto.NewGroupDto;
import com.srikar.tally.users.Users;


import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<GroupDto> getUserGroups(String userId);
    Optional<GroupDto> getGroupById(int groupId);
    Optional<Groups> findGroupById(int groupId);
    void addUserToGroup(Groups group, Users user);
    Optional<Integer> createGroup(String userId, NewGroupDto data);
    Optional<Boolean> updateGroup(int groupId, NewGroupDto dto);
    Optional<Boolean> deleteGroup(int groupId);
}
