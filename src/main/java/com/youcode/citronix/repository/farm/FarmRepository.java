package com.youcode.citronix.repository.farm;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.youcode.citronix.entity.farm.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long>, JpaSpecificationExecutor<Farm> {
    boolean existsByNameIgnoreCase(String name);
    List<Farm> findByIsDeletedFalse();
}
