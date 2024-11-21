package com.youcode.citronix.repository.farm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.farm.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long>, JpaSpecificationExecutor<Field> {
    boolean existsByNameIgnoreCaseAndFarmId(String name, Long farmId);
    Page<Field> findByIsDeletedFalse(Pageable pageable);
    Page<Field> findByFarmAndIsDeletedFalse(Farm farm, Pageable pageable);
}
