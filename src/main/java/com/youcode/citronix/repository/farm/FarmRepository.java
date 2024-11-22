package com.youcode.citronix.repository.farm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>, JpaSpecificationExecutor<Farm> {
    boolean existsByNameIgnoreCase(String name);
    Page<Farm> findByIsDeletedFalse(Pageable pageable);
}
