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
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    @Column(nullable = false)
    private LocalDateTime harvestDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Season season;

    private String description;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HarvestDetail> harvestDetails = new ArrayList<>();

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Sale> sales = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void addHarvestDetail(HarvestDetail detail) {
        harvestDetails.add(detail);
        detail.setHarvest(this);
    }

    public void removeHarvestDetail(HarvestDetail detail) {
        harvestDetails.remove(detail);
        detail.setHarvest(null);
    }

    public boolean isUniquePerSeason() {
        return farm.getHarvests().stream()
                .filter(h -> !h.equals(this) && !h.getIsDeleted())
                .noneMatch(h -> h.getSeason() == this.season);
    }

    public Double getTotalQuantity() {
        return harvestDetails.stream()
                .filter(detail -> !detail.getIsDeleted())
                .mapToDouble(HarvestDetail::getQuantity)
                .sum();
    }
}
