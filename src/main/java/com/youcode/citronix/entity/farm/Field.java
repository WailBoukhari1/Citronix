package com.youcode.citronix.entity.farm;

import com.youcode.citronix.entity.production.HarvestDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double area;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Tree> trees = new ArrayList<>();

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Business logic methods
    public int getActiveTreeCount() {
        return (int) trees.stream()
                .filter(tree -> !tree.getIsDeleted())
                .count();
    }

    public Double getTotalTreeProductivity() {
        return trees.stream()
                .filter(tree -> !tree.getIsDeleted())
                .mapToDouble(Tree::getSeasonalProductivity)
                .sum();
    }

    public boolean hasCapacityForTrees(int additionalTrees) {
        int currentTrees = getActiveTreeCount();
        // Assuming 100 trees per hectare = 1 tree per 100m²
        double maxTrees = area / 100;
        return (currentTrees + additionalTrees) <= maxTrees;
    }

    public boolean isAreaValid() {
        return area <= farm.getArea() - farm.getFields().stream()
                .filter(field -> !field.getIsDeleted() && !field.equals(this))
                .mapToDouble(Field::getArea)
                .sum();
    }

    public boolean isActive() {
        return !getIsDeleted() && !farm.getIsDeleted();
    }

    public Double getMaxTreeCapacity() {
        return area / 100; // 100m² per tree
    }

    @PrePersist
    @PreUpdate
    protected void validateField() {
        if (!isAreaValid()) {
            throw new IllegalStateException("Field area exceeds available farm area");
        }
        if (getActiveTreeCount() > getMaxTreeCapacity()) {
            throw new IllegalStateException("Field contains more trees than its capacity");
        }
    }
}
