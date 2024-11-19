package com.youcode.citronix.entity.production;

import com.youcode.citronix.entity.farm.Field;
import com.youcode.citronix.entity.farm.Tree;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HarvestDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harvest_id", nullable = false)
    private Harvest harvest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tree_id", nullable = false)
    private Tree tree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @Version
    private Long version;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isFieldValid() {
        return field.equals(tree.getField());
    }

    public boolean isActive() {
        return !isDeleted && !harvest.getIsDeleted() && !tree.getIsDeleted() && !field.isActive();
    }
}