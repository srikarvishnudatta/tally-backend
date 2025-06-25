package com.srikar.tally.groups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroups, Integer> {
    @Query("SELECT ug from UserGroups ug where ug.user.id = :userId")
    List<UserGroups> findGroupsByUserId(@Param("userId") String userId);
}
