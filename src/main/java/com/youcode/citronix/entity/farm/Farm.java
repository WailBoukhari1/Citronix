package com.youcode.citronix.entity.farm;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = false")
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Farm name is required")
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Area is required")
    @Min(value = 1000, message = "Farm area must be at least 1000 square meters")
    @Column(nullable = false)
    private Double area;

    @Column(nullable = false)
    private LocalDate creationDate;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (creationDate == null) {
            creationDate = LocalDate.now();
        }
    }

    // Business logic methods
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

    public boolean canAddField(Double fieldArea) {
        // Check if adding this field would exceed the maximum number of fields (10)
        if (getActiveFieldCount() >= 10) {
            return false;
        }

        // Check if the field area is within allowed limits (0.1 to 50% of farm area)
        if (fieldArea < 1000 || fieldArea > (area * 0.5)) {
            return false;
        }

        // Check if total field area would remain less than farm area
        double newTotalArea = getTotalFieldArea() + fieldArea;
        return newTotalArea < area;
    }
}
