package com.srikar.tally.friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitations, Integer> {
    int countAllByReceiver_Id(String receiverId);
}
