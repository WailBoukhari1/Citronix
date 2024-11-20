package com.youcode.citronix.repository.production;

import com.youcode.citronix.entity.production.Harvest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Long> {
    List<Harvest> findByIsDeletedFalse();
}
