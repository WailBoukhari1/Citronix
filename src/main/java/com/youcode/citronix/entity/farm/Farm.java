package com.youcode.citronix.entity.farm;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.youcode.citronix.entity.production.Harvest;
import com.youcode.citronix.entity.sales.Sale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Builder.Default
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Harvest> harvests = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Sale> sales = new ArrayList<>();

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Core business logic methods
    public Double getTotalFieldArea() {
        return fields.stream()
                .filter(field -> !field.getIsDeleted())
                .mapToDouble(Field::getArea)
                .sum();
    }

    public Integer getActiveFieldCount() {
        return (int) fields.stream()
                .filter(field -> !field.getIsDeleted())
                .count();
    }

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
    }

    @PreUpdate
    protected void validate() {
        if (getTotalFieldArea() > area) {
            throw new IllegalStateException("Total field area cannot exceed farm area");
        }
    }

    // Example method for multi-criteria search
    public static List<Farm> searchFarms(List<Farm> farms, String name, String location, Double minArea, Double maxArea) {
        return farms.stream()
                .filter(farm -> (name == null || farm.getName().contains(name)) &&
                                (location == null || farm.getLocation().contains(location)) &&
                                (minArea == null || farm.getArea() >= minArea) &&
                                (maxArea == null || farm.getArea() <= maxArea))
                .collect(Collectors.toList());
    }
}