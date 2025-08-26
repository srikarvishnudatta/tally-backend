package com.srikar.tally.repository;

import com.srikar.tally.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {
    List<Groups> findGroupsByOwner_Id(String id);

    @Query("SELECT g from Groups g LEFT JOIN FETCH g.members m WHERE m.id=:userId")
    List<Groups> findByMembersId(@Param("userId") String userId);
}
