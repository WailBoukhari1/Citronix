package com.youcode.citronix.repository.production;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.enums.Season;
import com.youcode.citronix.entity.farm.Tree;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long>, JpaSpecificationExecutor<Harvest> {
    Page<Harvest> findAllByIsDeletedFalse(Pageable pageable);
    boolean existsByFarmAndSeasonAndIsDeletedFalse(Farm farm, Season season);
    
    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Harvest h " +
           "JOIN h.harvestDetails hd " +
           "WHERE hd.tree = :tree AND h.season = :season AND h.isDeleted = false AND hd.isDeleted = false")
    boolean existsByTreeAndSeasonAndIsDeletedFalse(@Param("tree") Tree tree, @Param("season") Season season);
}