package com.youcode.citronix.entity.sales;

import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.production.Harvest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate saleDate;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double pricePerUnit;

    @Column(nullable = false)
    private String clientName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Business logic methods
    public Double getRevenue() {
        return quantity * pricePerUnit;
    }

    public boolean isQuantityValid() {
        return harvest.hasRemainingQuantity(quantity);
    }

    public boolean isActive() {
        return !getIsDeleted() && !harvest.getIsDeleted() && !farm.getIsDeleted();
    }

    public boolean isPriceReasonable() {
        Double avgPrice = harvest.getAveragePrice();
        // Price is considered reasonable if it's within ±20% of average
        return avgPrice == 0 || 
               (pricePerUnit >= avgPrice * 0.8 && pricePerUnit <= avgPrice * 1.2);
    }

    public boolean isSaleDateValid() {
        return !saleDate.isBefore(harvest.getHarvestDate());
    }
}