package com.youcode.citronix.repository.sales;

import com.youcode.citronix.entity.sales.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByIsDeletedFalse();

    @Query("SELECT s FROM Sale s WHERE s.harvest.id = :harvestId AND s.isDeleted = false")
    List<Sale> findByHarvestId(Long harvestId);

    @Query("SELECT s FROM Sale s WHERE s.farm.id = :farmId AND s.isDeleted = false")
    List<Sale> findByFarmId(Long farmId);

    @Query("SELECT SUM(s.quantity) FROM Sale s WHERE s.harvest.id = :harvestId AND s.isDeleted = false")
    Double getTotalSoldQuantityByHarvestId(Long harvestId);

    @Query("SELECT AVG(s.pricePerUnit) FROM Sale s WHERE s.harvest.id = :harvestId AND s.isDeleted = false")
    Double getAveragePriceByHarvestId(Long harvestId);
}
