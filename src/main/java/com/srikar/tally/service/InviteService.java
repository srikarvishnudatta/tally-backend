package com.srikar.tally.service;

import com.srikar.tally.dto.invite.InviteMapper;
import com.srikar.tally.dto.invite.InviteRequestDto;
import com.srikar.tally.dto.invite.InviteResponseDto;
import com.srikar.tally.exception.InviteNotValidException;
import com.srikar.tally.model.Invite;
import com.srikar.tally.model.InviteStatus;
import com.srikar.tally.repository.InviteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteService {
    private final InviteRepository inviteRepository;
    private final UserService userService;
    private final GroupService groupService;

    public InviteService(InviteRepository inviteRepository,
                         UserService userService,
                         GroupService groupService) {
        this.inviteRepository = inviteRepository;
        this.userService = userService;
        this.groupService = groupService;
    }

    public List<InviteResponseDto> getInvitations(String userId){
        var invites = inviteRepository.findAllByReceiver_Id(userId);
        return invites.stream().map(InviteMapper::toDto).toList();
    }
    public List<InviteResponseDto> getSentInvitations(String userId){
        var sentInvites = inviteRepository.getInvitesBySender_Id(userId);
        return sentInvites.stream().map(InviteMapper::toDto).toList();
    }
    public InviteResponseDto createInvite(String userId, InviteRequestDto dto){
        var user = userService.getUserById(userId);
        var receiver = userService.getUserByEmail(dto.getReceiverEmail());
        var group = groupService.findById(dto.getGroupId());
        var inviteSent = Invite.builder()
                .sender(user)
                .receiver(receiver)
                .group(group)
                .status(InviteStatus.SENT)
                .build();
        inviteRepository.save(inviteSent);
        return InviteMapper.toDto(inviteSent);
    }
    public void acceptInvite(String userId, int inviteId){
        var user = userService.getUserById(userId);
        var invite = findInviteById(inviteId);
        if(!invite.getReceiver().getId().equals(user.getId())){
            throw new InviteNotValidException("This user is not allowed to accept this invite");
        }
        if(invite.getStatus() != InviteStatus.SENT){
            throw new InviteNotValidException("Invite is no longer valid");
        }
        var group = invite.getGroup();
        group.getMembers().add(user);
        deleteInvite(inviteId);
        groupService.saveGroup(group);
    }
    public void declineInvite(String userId, int inviteId){
        userService.getUserById(userId);
        findInviteById(inviteId);
        deleteInvite(inviteId);
    }
    public Invite findInviteById(int inviteId){
        return inviteRepository
                .findById(inviteId)
                .orElseThrow(() -> new InviteNotValidException("Invite does not exist"));
    }
    public void deleteInvite(int id){
        inviteRepository.deleteById(id);
    }
}
