package com.srikar.tally.repository;

import com.srikar.tally.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {
    @Query("SELECT g FROM Groups g LEFT JOIN FETCH g.members " +
            "WHERE EXISTS (SELECT 1 FROM Groups g2 JOIN g2.members m " +
            "WHERE g2.id = g.id AND m.id = :userId)")
    List<Groups> findByMembersId(@Param("userId") String userId);
}
