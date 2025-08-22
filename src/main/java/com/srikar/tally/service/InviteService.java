package com.srikar.tally.service;

import com.srikar.tally.dto.invite.InviteMapper;
import com.srikar.tally.dto.invite.InviteResponseDto;
import com.srikar.tally.exception.InviteNotValidException;
import com.srikar.tally.exception.UserNotFoundException;
import com.srikar.tally.model.InviteStatus;
import com.srikar.tally.repository.GroupRepository;
import com.srikar.tally.repository.InviteRepository;
import com.srikar.tally.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public InviteService(InviteRepository inviteRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.inviteRepository = inviteRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public List<InviteResponseDto> getInvitations(String userId){
        var invites = inviteRepository.findAllByReceiver_Id(userId);
        return invites.stream().map(InviteMapper::toDto).toList();
    }
    public List<InviteResponseDto> getSentInvitations(String userId){
        var sentInvites = inviteRepository.getInvitesBySender_Id(userId);
        return sentInvites.stream().map(InviteMapper::toDto).toList();
    }

    public void acceptInvite(String userId, int inviteId){
        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User cannot be found"));
        var invite = inviteRepository
                .findById(inviteId)
                .orElseThrow(() -> new InviteNotValidException("Invite does not exist"));
        if(!invite.getReceiver().getId().equals(user.getId())){
            throw new InviteNotValidException("This user is not allowed to accept this invite");
        }
        if(invite.getStatus() != InviteStatus.RECEIVED){
            throw new InviteNotValidException("Invite is no longer valid");
        }
        var group = invite.getGroup();
        group.getMembers().add(user);
        deleteInvite(inviteId);
        groupRepository.save(group);
    }
    public void declineInvite(String userId, int inviteId){
        var user = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User cannot be found"));
        var invite = inviteRepository
                .findById(inviteId)
                .orElseThrow(() -> new InviteNotValidException("Invite does not exist"));
        deleteInvite(inviteId);
    }
    public void deleteInvite(int id){
        inviteRepository.deleteById(id);
    }
}
