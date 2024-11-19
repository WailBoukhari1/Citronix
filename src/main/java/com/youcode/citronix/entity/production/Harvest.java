package com.youcode.citronix.entity.production;

import com.youcode.citronix.entity.base.BaseEntity;
import com.youcode.citronix.entity.enums.HarvestStatus;
import com.youcode.citronix.entity.enums.Season;
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
@Table(name = "harvests")
public class Harvest extends BaseEntity {
    
    @NotNull(message = "Harvest date is required")
    @PastOrPresent(message = "Harvest date cannot be in the future")
    @Column(nullable = false)
    private LocalDate harvestDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HarvestStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;
    
    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HarvestDetail> harvestDetails = new ArrayList<>();
    
    @Transient
    public double getTotalQuantity() {
        return harvestDetails.stream()
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();
    }
}
