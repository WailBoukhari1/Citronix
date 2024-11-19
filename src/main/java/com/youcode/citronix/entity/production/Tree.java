package com.youcode.citronix.entity.production;

import com.youcode.citronix.entity.base.BaseEntity;
import com.youcode.citronix.entity.enums.TreeHealth;
import com.youcode.citronix.entity.farm.Field;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "trees")
public class Tree extends BaseEntity {
    
    @NotNull(message = "Planting date is required")
    @PastOrPresent(message = "Planting date cannot be in the future")
    @Column(nullable = false)
    private LocalDate plantingDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TreeHealth health;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;
    
    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HarvestDetail> harvestDetails = new ArrayList<>();
    
    @Transient
    public int getAge() {
        return LocalDate.now().getYear() - plantingDate.getYear();
    }
    
    @Transient
    public double getProductivityPerSeason() {
        int age = getAge();
        if (age < 3) return 2.5; // young tree
        if (age <= 10) return 12.0; // mature tree
        if (age <= 20) return 20.0; // old tree
        return 0.0; // non-productive
    }
    
    @PrePersist
    @PreUpdate
    private void validatePlantingDate() {
        if (plantingDate != null) {
            int month = plantingDate.getMonthValue();
            if (month < 3 || month > 5) {
                throw new IllegalStateException("Trees can only be planted between March and May");
            }
        }
    }
}
