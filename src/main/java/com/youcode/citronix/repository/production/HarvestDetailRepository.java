package com.youcode.citronix.repository.production;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Tree;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.production.HarvestDetail;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long>, JpaSpecificationExecutor<HarvestDetail> {
    Page<HarvestDetail> findByIsDeletedFalse(Pageable pageable);
    Page<HarvestDetail> findByHarvestAndIsDeletedFalse(Harvest harvest, Pageable pageable);
    Page<HarvestDetail> findByTreeAndIsDeletedFalse(Tree tree, Pageable pageable);
    
    List<HarvestDetail> findByTree_IdAndIsDeletedFalse(Long treeId);
    Page<HarvestDetail> findAllByHarvestIdAndIsDeletedFalse(Long harvestId, Pageable pageable);
}