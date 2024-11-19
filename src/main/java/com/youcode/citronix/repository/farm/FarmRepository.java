package com.youcode.citronix.repository.farm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.youcode.citronix.entity.farm.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Farm> findByIsDeletedFalse();
}
