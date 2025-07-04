package com.srikar.tally.expenses.repository;

import com.srikar.tally.expenses.model.Balances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balances, Integer> {
    Optional<Balances> findByUser_IdAndOwesTo_IdAndBalanceGroups_Id(String userId, String owesToId, int groupId);
}
