package com.youcode.citronix.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.production.HarvestDetail;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    List<HarvestDetail> findByIsDeletedFalse();
    List<HarvestDetail> findByHarvestIdAndIsDeletedFalse(Long harvestId);
    List<HarvestDetail> findByTreeIdAndIsDeletedFalse(Long treeId);
    List<HarvestDetail> findByFieldIdAndIsDeletedFalse(Long fieldId);
}
