package com.youcode.citronix.entity.farm;

import com.youcode.citronix.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "farms")
public class Farm extends BaseEntity {
    
    @NotBlank(message = "Farm name is required")
    @Column(nullable = false)
    private String name;
    
    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;
    
    @NotNull(message = "Area is required")
    @Min(value = 10000, message = "Minimum farm size is 1 hectare (10,000 m²)")
    @Column(nullable = false)
    private Double area; // in square meters
    
    @NotNull(message = "Creation date is required")
    @PastOrPresent(message = "Creation date cannot be in the future")
    @Column(nullable = false)
    private LocalDate creationDate;
    
    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Field> fields = new ArrayList<>();
    
    @PrePersist
    @PreUpdate
    private void validateFieldsConstraints() {
        // Constraint: Maximum 10 fields per farm
        if (fields.size() > 10) {
            throw new IllegalStateException("A farm cannot have more than 10 fields");
        }
        
        // Constraint: Total field area must be less than farm area
        double totalFieldArea = fields.stream()
                .filter(field -> !field.getIsDeleted())
                .mapToDouble(Field::getArea)
                .sum();
        
        if (totalFieldArea >= area) {
            throw new IllegalStateException("Total field area cannot exceed farm area");
        }
        
        // Constraint: No field can exceed 50% of farm area
        double maxAllowedFieldArea = area * 0.5;
        Optional<Field> oversizedField = fields.stream()
                .filter(field -> !field.getIsDeleted())
                .filter(field -> field.getArea() > maxAllowedFieldArea)
                .findFirst();
                
        if (oversizedField.isPresent()) {
            throw new IllegalStateException("Field area cannot exceed 50% of farm area");
        }
    }
}
