package com.youcode.citronix.repository.production;

import com.youcode.citronix.entity.production.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByIsDeletedFalse();

    @Query("SELECT h FROM Harvest h " +
           "JOIN h.harvestDetails hd " +
           "WHERE hd.tree.id = :treeId " +
           "AND h.harvestDate BETWEEN :startDate AND :endDate " +
           "AND h.isDeleted = false")
    List<Harvest> findByTreeAndDateRange(Long treeId, LocalDateTime startDate, LocalDateTime endDate);
}
