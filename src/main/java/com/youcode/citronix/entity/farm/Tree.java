package com.youcode.citronix.entity.farm;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

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

    @Column(nullable = false)
    private LocalDate plantationDate;

    public int getAge() {
        return Period.between(plantationDate, LocalDate.now()).getYears();
    }

    public double getProductivity() {
        int age = getAge();
        if (age < 3) {
            return 2.5;
        } else if (age <= 10) {
            return 12.0;
        } else {
            return 20.0;
        }
    }

    public double calculateSeasonalProductivity() {
        int age = calculateAge();
        
        if (age > 20) {
            return 0.0; // Non-productive after 20 years
        } else if (age > 10) {
            return 20.0; // Old tree (>10 years): 20 kg/season
        } else if (age >= 3) {
            return 12.0; // Mature tree (3-10 years): 12 kg/season
        } else {
            return 2.5; // Young tree (<3 years): 2.5 kg/season
        }
    }

    private int calculateAge() {
        return Period.between(plantationDate, LocalDate.now()).getYears();
    }
}
