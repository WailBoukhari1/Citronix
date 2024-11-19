package com.youcode.citronix.repository.farm;

import com.youcode.citronix.entity.farm.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long>, JpaSpecificationExecutor<Farm> {
    boolean existsByNameIgnoreCase(String name);
    
    @Query("SELECT f FROM Farm f LEFT JOIN FETCH f.fields WHERE f.id = :id AND f.isDeleted = false")
    Optional<Farm> findByIdWithFields(Long id);
    
    @Query("SELECT SUM(f.area) FROM Field f WHERE f.farm.id = :farmId AND f.isDeleted = false")
    Double getTotalFieldsArea(Long farmId);
    
    @Query("SELECT COUNT(f) FROM Field f WHERE f.farm.id = :farmId AND f.isDeleted = false")
    Long countFieldsByFarmId(Long farmId);
}
