package com.youcode.citronix.repository.production;

import com.youcode.citronix.entity.production.HarvestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HarvestDetailRepository extends JpaRepository<HarvestDetail, Long> {
    List<HarvestDetail> findByHarvestIdAndIsDeletedFalse(Long harvestId);
}
