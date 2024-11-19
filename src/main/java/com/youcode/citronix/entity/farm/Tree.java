package com.youcode.citronix.entity.farm;

import com.youcode.citronix.entity.production.HarvestDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Formula;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate plantingDate;

    @Formula("DATEDIFF('YEAR', planting_date, CURRENT_DATE)")
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Business logic methods
    public Double getSeasonalProductivity() {
        if (age < 3) return 0.0;      // Too young
        if (age < 5) return 5.0;      // Young tree
        if (age <= 10) return 12.0;   // Mature tree
        return 20.0;                  // Old tree
    }

    public Double calculateTotalProduction(int year) {
        return harvestDetails.stream()
                .filter(detail -> !detail.getIsDeleted() && 
                        detail.getHarvest().getYear().equals(year))
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();
    }

    public Double calculateAverageProduction() {
        if (harvestDetails.isEmpty()) return 0.0;
        
        long activeHarvests = harvestDetails.stream()
                .filter(detail -> !detail.getIsDeleted())
                .count();
                
        if (activeHarvests == 0) return 0.0;
        
        return harvestDetails.stream()
                .filter(detail -> !detail.getIsDeleted())
                .mapToDouble(HarvestDetail::getQuantity)
                .sum() / activeHarvests;
    }

    public boolean isActive() {
        return !getIsDeleted() && !field.getIsDeleted();
    }

    @PrePersist
    @PreUpdate
    protected void validateTree() {
        if (!field.hasCapacityForTrees(1)) {
            throw new IllegalStateException("Field has reached its maximum tree capacity");
        }
    }
}
