package com.youcode.citronix.repository.sales;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.sales.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findByIsDeletedFalse(Pageable pageable);

    Page<Sale> findByHarvestAndIsDeletedFalse(Harvest harvest, Pageable pageable);

    Page<Sale> findByFarmAndIsDeletedFalse(Farm farm, Pageable pageable);

    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.harvest.id = :harvestId AND s.isDeleted = false")
    Double getTotalSoldQuantityByHarvestId(Long harvestId);

    @Query("SELECT AVG(s.pricePerUnit) FROM Sale s WHERE s.harvest.id = :harvestId AND s.isDeleted = false")
    Double getAveragePriceByHarvestId(Long harvestId);
}
