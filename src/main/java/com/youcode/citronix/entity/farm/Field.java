package com.youcode.citronix.entity.farm;

import com.youcode.citronix.entity.base.BaseEntity;
import com.youcode.citronix.entity.production.Tree;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "fields")
public class Field extends BaseEntity {
    
    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Minimum field size is 0.1 hectare (1,000 m²)")
    @Max(value = 10000000, message = "Maximum field size is 1000 hectares (10,000,000 m²)")
    @Column(nullable = false)
    private Double area;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;
    
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Tree> trees = new ArrayList<>();
    
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
    
    @Version
    private Long version;
    
    @PrePersist
    @PreUpdate
    private void validateConstraints() {
        // Constraint: Field cannot exceed 50% of farm area
        if (farm != null && area > farm.getArea() * 0.5) {
            throw new IllegalStateException("Field area cannot exceed 50% of farm area");
        }
        
        // Constraint: Tree density (100 trees per hectare)
        double maxTrees = area / 100; // 1 tree per 100m²
        if (trees.size() > maxTrees) {
            throw new IllegalStateException(
                String.format("Maximum tree density exceeded. Maximum allowed: %.0f trees", maxTrees));
        }
    }
}
