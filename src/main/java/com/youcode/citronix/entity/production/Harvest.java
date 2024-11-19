package com.youcode.citronix.entity.production;

import com.youcode.citronix.entity.enums.Season;
import com.youcode.citronix.entity.farm.Farm;
import com.youcode.citronix.entity.sales.Sale;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    @Column(nullable = false)
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<Sale> sales = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Business logic methods
    public Double getTotalQuantity() {
        return details.stream()
                .filter(detail -> !detail.getIsDeleted())
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();
    }

    public boolean isValidSeason() {
        return Season.values()[harvestDate.getMonth().ordinal() / 3] == season;
    }

    public Double getTotalRevenue() {
        return sales.stream()
                .filter(sale -> !sale.getIsDeleted())
                .mapToDouble(Sale::getRevenue)
                .sum();
    }

    public Double getAveragePrice() {
        if (sales.isEmpty()) return 0.0;
        
        long activeSales = sales.stream()
                .filter(sale -> !sale.getIsDeleted())
                .count();
                
        if (activeSales == 0) return 0.0;
        
        return getTotalRevenue() / getTotalQuantity();
    }

    public boolean hasRemainingQuantity(Double requestedQuantity) {
        return getRemainingQuantity() >= requestedQuantity;
    }

    public Double getRemainingQuantity() {
        Double soldQuantity = sales.stream()
                .filter(sale -> !sale.getIsDeleted())
                .mapToDouble(Sale::getQuantity)
                .sum();
        
        return getTotalQuantity() - soldQuantity;
    }

    public boolean isActive() {
        return !getIsDeleted() && !farm.getIsDeleted();
    }

    @PrePersist
    @PreUpdate
    protected void validateHarvest() {
        if (!isValidSeason()) {
            throw new IllegalStateException("Harvest season does not match the harvest date");
        }
    }
}