package com.srikar.tally.groups.repository;

import com.srikar.tally.groups.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups, Integer> {
}
