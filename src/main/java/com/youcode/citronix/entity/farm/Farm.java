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

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Field> fields = new ArrayList<>();

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Harvest> harvests = new ArrayList<>();

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL)
    private List<Sale> sales = new ArrayList<>();

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
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
    protected void validateFarm() {
        if (getTotalFieldArea() > area) {
            throw new IllegalStateException("Total field area cannot exceed farm area");
        }
    }
}