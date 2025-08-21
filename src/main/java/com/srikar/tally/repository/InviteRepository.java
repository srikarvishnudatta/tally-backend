package com.srikar.tally.repository;

import com.srikar.tally.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Integer> {
    List<Invite> findAllByReceiver_Id(String userId);
    List<Invite> getInvitesBySender_Id(String userId);
}
