package com.youcode.citronix.repository.farm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.farm.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByIsDeletedFalse();
    Page<Field> findByIsDeletedFalse(Pageable pageable);
    List<Field> findByFarmAndIsDeletedFalse(Farm farm);
    Page<Field> findByFarmAndIsDeletedFalse(Farm farm, Pageable pageable);
    boolean existsByNameIgnoreCaseAndFarmId(String name, Long farmId);
}
