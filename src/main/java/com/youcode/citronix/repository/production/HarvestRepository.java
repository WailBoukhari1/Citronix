package com.youcode.citronix.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.production.Harvest;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByIsDeletedFalse();
    List<Harvest> findByFarmIdAndIsDeletedFalse(Long farmId);
}
